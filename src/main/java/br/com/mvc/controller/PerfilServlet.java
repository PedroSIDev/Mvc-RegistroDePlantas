package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
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
        if (acao != null && !acao.isBlank()) {
            try {
                if ("excluir".equals(acao)) {
                    throw new IllegalArgumentException("Para excluir, use o botão de exclusão e confirme a operação.");
                }
                if (!"novo".equals(acao) && !"editar".equals(acao)) {
                    throw new IllegalArgumentException("Ação inválida. Selecione uma opção da lista.");
                }
                Perfil perfil = "novo".equals(acao) ? new Perfil()
                        : perfilService.buscarPorId(idObrigatorio(request));
                if (perfil == null) {
                    throw new IllegalArgumentException("Perfil não encontrado. O registro pode ter sido excluído.");
                }
                request.setAttribute("perfil", perfil);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/perfis");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/perfis/form.jsp");
            return;
        }

        try {
            request.setAttribute("perfis", perfilService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/perfis/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                perfilService.excluir(idObrigatorio(request));
                mensagem(request, true, "Perfil excluído com sucesso.");
                redirect(request, response, "/perfis");
                return;
            }
            id = idFormulario(request);
            if (id != null && perfilService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Perfil não encontrado. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/perfis");
            return;
        }

        Perfil perfil = new Perfil();
        perfil.setId(id);
        perfil.setNome(request.getParameter("nome"));
        request.setAttribute("perfil", perfil);

        List<String> erros = new ArrayList<>();
        try {
            erros.addAll(perfilService.validar(perfil));
            if (erros.isEmpty()) {
                if (id == null) {
                    perfilService.inserir(perfil);
                } else {
                    perfilService.alterar(perfil);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/perfis/form.jsp");
            return;
        }

        mensagem(request, true, id == null ? "Perfil cadastrado com sucesso."
                : "Perfil atualizado com sucesso.");
        redirect(request, response, "/perfis");
    }
}
