package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Cuidado;
import br.com.mvc.service.CuidadoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cuidados")
public class CuidadoServlet extends BaseServlet {

    private final CuidadoService cuidadoService = new CuidadoService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao != null && !acao.isBlank()) {
            try {
                if ("excluir".equals(acao)) {
                    throw new IllegalArgumentException("Para excluir, use o botão de exclusão e confirme a operação.");
                }
                if (!"novo".equals(acao) && !"editar".equals(acao)) {
                    throw new IllegalArgumentException("Ação inválida. Selecione uma opção da lista.");
                }
                Cuidado cuidado = "novo".equals(acao) ? new Cuidado()
                        : cuidadoService.buscarPorId(idObrigatorio(request));
                if (cuidado == null) {
                    throw new IllegalArgumentException("Cuidado não encontrado. O registro pode ter sido excluído.");
                }
                request.setAttribute("cuidado", cuidado);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/cuidados");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/cuidados/form.jsp");
            return;
        }

        try {
            request.setAttribute("cuidados", cuidadoService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/cuidados/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                cuidadoService.excluir(idObrigatorio(request));
                mensagem(request, true, "Cuidado excluído com sucesso.");
                redirect(request, response, "/cuidados");
                return;
            }
            id = idFormulario(request);
            if (id != null && cuidadoService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Cuidado não encontrado. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/cuidados");
            return;
        }

        Cuidado cuidado = new Cuidado();
        cuidado.setId(id);
        cuidado.setNome(request.getParameter("nome"));
        cuidado.setDescricao(request.getParameter("descricao"));
        try {
            cuidado.setDiasIntervalo(Integer.parseInt(request.getParameter("diasIntervalo").trim()));
        } catch (NumberFormatException | NullPointerException e) {
            cuidado.setDiasIntervalo(0);
        }
        request.setAttribute("cuidado", cuidado);

        List<String> erros = new ArrayList<>();
        try {
            erros.addAll(cuidadoService.validar(cuidado));
            if (erros.isEmpty()) {
                if (id == null) {
                    cuidadoService.inserir(cuidado);
                } else {
                    cuidadoService.alterar(cuidado);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/cuidados/form.jsp");
            return;
        }

        mensagem(request, true, id == null ? "Cuidado cadastrado com sucesso."
                : "Cuidado atualizado com sucesso.");
        redirect(request, response, "/cuidados");
    }
}
