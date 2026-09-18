package br.com.mvc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public abstract class BaseServlet extends HttpServlet {

    protected void forward(HttpServletRequest request, HttpServletResponse response, String destino)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        synchronized (session) {
            if (session.getAttribute("csrfToken") == null) {
                session.setAttribute("csrfToken", UUID.randomUUID().toString());
            }
        }
        request.getRequestDispatcher(destino).forward(request, response);
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response, String destino)
            throws IOException {
        response.sendRedirect(request.getContextPath() + destino);
    }

    protected Long parseId(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            long id = Long.parseLong(valor.trim());
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Long idObrigatorio(HttpServletRequest request) {
        Long id = parseId(request.getParameter("id"));
        if (id == null) {
            throw new IllegalArgumentException("Identificador inválido. Selecione o registro novamente na lista.");
        }
        return id;
    }

    protected Long idFormulario(HttpServletRequest request) {
        String valor = request.getParameter("id");
        return valor == null || valor.isBlank() ? null : idObrigatorio(request);
    }

    protected void validarFormulario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String token = request.getParameter("csrfToken");
        if (session == null || token == null || !token.equals(session.getAttribute("csrfToken"))) {
            throw new IllegalArgumentException("Este formulário expirou. Atualize a página e tente novamente.");
        }
        String acao = request.getParameter("acao");
        if (acao != null && !"salvar".equals(acao) && !"excluir".equals(acao)) {
            throw new IllegalArgumentException("Ação inválida. Atualize a página e tente novamente.");
        }
    }

    protected void mensagem(HttpServletRequest request, boolean sucesso, String texto) {
        request.getSession().removeAttribute("mensagemSucesso");
        request.getSession().removeAttribute("mensagemErro");
        request.getSession().setAttribute(sucesso ? "mensagemSucesso" : "mensagemErro", texto);
    }

    protected String mensagemErro(RuntimeException erro) {
        if (erro instanceof IllegalArgumentException) {
            return erro.getMessage();
        }
        log("Não foi possível concluir a operação.", erro);
        for (Throwable causa = erro; causa != null; causa = causa.getCause()) {
            if (causa instanceof SQLException) {
                int codigo = ((SQLException) causa).getErrorCode();
                if (codigo == 1451) {
                    return "Não é possível excluir: há registros vinculados. Remova ou transfira os vínculos primeiro.";
                }
                if (codigo == 1452) {
                    return "Um dos registros selecionados não existe mais. Atualize a página e revise os vínculos.";
                }
                if (codigo == 1062) {
                    return "Já existe um registro com esses dados. Escolha outro valor e tente novamente.";
                }
                if (codigo == 1205 || codigo == 1213) {
                    return "Este registro está sendo alterado por outra operação. Aguarde um momento e tente novamente.";
                }
            }
        }
        return "Não foi possível concluir a operação. Atualize a página para conferir os dados e tente novamente.";
    }
}
