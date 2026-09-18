package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
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
        if (acao != null && !acao.isBlank()) {
            try {
                if ("excluir".equals(acao)) {
                    throw new IllegalArgumentException("Para excluir, use o botão de exclusão e confirme a operação.");
                }
                if (!"novo".equals(acao) && !"editar".equals(acao)) {
                    throw new IllegalArgumentException("Ação inválida. Selecione uma opção da lista.");
                }
                Ambiente ambiente = "novo".equals(acao) ? new Ambiente()
                        : ambienteService.buscarPorId(idObrigatorio(request));
                if (ambiente == null) {
                    throw new IllegalArgumentException("Ambiente não encontrado. O registro pode ter sido excluído.");
                }
                request.setAttribute("ambiente", ambiente);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/ambientes");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/ambientes/form.jsp");
            return;
        }

        try {
            request.setAttribute("ambientes", ambienteService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/ambientes/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                ambienteService.excluir(idObrigatorio(request));
                mensagem(request, true, "Ambiente excluído com sucesso.");
                redirect(request, response, "/ambientes");
                return;
            }
            id = idFormulario(request);
            if (id != null && ambienteService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Ambiente não encontrado. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/ambientes");
            return;
        }

        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        ambiente.setNome(request.getParameter("nome"));
        ambiente.setDescricao(request.getParameter("descricao"));
        request.setAttribute("ambiente", ambiente);

        List<String> erros = new ArrayList<>();
        try {
            erros.addAll(ambienteService.validar(ambiente));
            if (erros.isEmpty()) {
                if (id == null) {
                    ambienteService.inserir(ambiente);
                } else {
                    ambienteService.alterar(ambiente);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/ambientes/form.jsp");
            return;
        }

        mensagem(request, true, id == null ? "Ambiente cadastrado com sucesso."
                : "Ambiente atualizado com sucesso.");
        redirect(request, response, "/ambientes");
    }
}
