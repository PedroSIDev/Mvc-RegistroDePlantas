package br.com.mvc.controller;

import java.io.IOException;
import java.util.List;

import br.com.mvc.model.Perfil;
import br.com.mvc.service.PerfilService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/perfis")
public class PerfilServlet extends BaseServlet {

    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("novo".equals(acao)) {
            request.setAttribute("perfil", new Perfil());
            forward(request, response, "/WEB-INF/jsp/perfis/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Perfil perfil = perfilService.buscarPorId(id);
            request.setAttribute("perfil", perfil);
            forward(request, response, "/WEB-INF/jsp/perfis/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                perfilService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Perfil excluído com sucesso.");
            }
            redirect(request, response, "/perfis");
            return;
        }

        List<Perfil> perfis = perfilService.listarTodos();
        request.setAttribute("perfis", perfis);
        forward(request, response, "/WEB-INF/jsp/perfis/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        String nome = request.getParameter("nome");

        Perfil perfil = new Perfil();
        perfil.setId(id);
        perfil.setNome(nome);

        List<String> erros = perfilService.validar(perfil);
        if (!erros.isEmpty()) {
            request.setAttribute("perfil", perfil);
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/perfis/form.jsp");
            return;
        }

        if (perfil.getId() == null) {
            perfilService.inserir(perfil);
            request.getSession().setAttribute("mensagemSucesso", "Perfil cadastrado com sucesso.");
        } else {
            perfilService.alterar(perfil);
            request.getSession().setAttribute("mensagemSucesso", "Perfil atualizado com sucesso.");
        }

        redirect(request, response, "/perfis");
    }
}
