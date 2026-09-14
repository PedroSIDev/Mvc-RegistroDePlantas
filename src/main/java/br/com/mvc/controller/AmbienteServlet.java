package br.com.mvc.controller;

import java.io.IOException;
import java.util.List;

import br.com.mvc.model.Ambiente;
import br.com.mvc.service.AmbienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ambientes")
public class AmbienteServlet extends BaseServlet {

    private final AmbienteService ambienteService = new AmbienteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("novo".equals(acao)) {
            request.setAttribute("ambiente", new Ambiente());
            forward(request, response, "/WEB-INF/jsp/ambientes/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Ambiente ambiente = ambienteService.buscarPorId(id);
            if (ambiente == null) {
                request.setAttribute("mensagemErro", "Ambiente não encontrado.");
            }
            request.setAttribute("ambiente", ambiente);
            forward(request, response, "/WEB-INF/jsp/ambientes/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                ambienteService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Ambiente excluído com sucesso.");
            }
            redirect(request, response, "/ambientes");
            return;
        }

        List<Ambiente> ambientes = ambienteService.listarTodos();
        request.setAttribute("ambientes", ambientes);
        forward(request, response, "/WEB-INF/jsp/ambientes/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");

        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        ambiente.setNome(nome);
        ambiente.setDescricao(descricao);

        List<String> erros = ambienteService.validar(ambiente);
        if (!erros.isEmpty()) {
            request.setAttribute("ambiente", ambiente);
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/ambientes/form.jsp");
            return;
        }

        if (ambiente.getId() == null) {
            ambienteService.inserir(ambiente);
            request.getSession().setAttribute("mensagemSucesso", "Ambiente cadastrado com sucesso.");
        } else {
            ambienteService.alterar(ambiente);
            request.getSession().setAttribute("mensagemSucesso", "Ambiente atualizado com sucesso.");
        }

        redirect(request, response, "/ambientes");
    }
}
