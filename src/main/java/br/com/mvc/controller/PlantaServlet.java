package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Planta;
import br.com.mvc.service.PlantaService;
import br.com.mvc.service.AmbienteService;
import br.com.mvc.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plantas")
public class PlantaServlet extends BaseServlet {

    private final PlantaService plantaService = new PlantaService();
    private final AmbienteService ambienteService = new AmbienteService();
    private final UsuarioService usuarioService = new UsuarioService();

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
                Planta planta = "novo".equals(acao) ? new Planta()
                        : plantaService.buscarPorId(idObrigatorio(request));
                if (planta == null) {
                    throw new IllegalArgumentException("Planta não encontrada. O registro pode ter sido excluído.");
                }
                request.setAttribute("planta", planta);
                carregarOpcoes(request);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/plantas");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/plantas/form.jsp");
            return;
        }

        try {
            request.setAttribute("plantas", plantaService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/plantas/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                plantaService.excluir(idObrigatorio(request));
                mensagem(request, true, "Planta excluída com sucesso.");
                redirect(request, response, "/plantas");
                return;
            }
            id = idFormulario(request);
            if (id != null && plantaService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Planta não encontrada. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/plantas");
            return;
        }

        Planta planta = new Planta();
        planta.setId(id);
        planta.setNomePopular(request.getParameter("nomePopular"));
        planta.setNomeCientifico(request.getParameter("nomeCientifico"));
        planta.setDataAquisicao(request.getParameter("dataAquisicao"));
        planta.setObservacoes(request.getParameter("observacoes"));
        planta.setUsuarioId(parseId(request.getParameter("usuarioId")));
        planta.setAmbienteId(parseId(request.getParameter("ambienteId")));
        request.setAttribute("planta", planta);

        List<String> erros = new ArrayList<>();
        try {
            carregarOpcoes(request);
            erros.addAll(plantaService.validar(planta));
            if (erros.isEmpty()) {
                if (id == null) {
                    plantaService.inserir(planta);
                } else {
                    plantaService.alterar(planta);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/plantas/form.jsp");
            return;
        }

        mensagem(request, true, id == null ? "Planta cadastrada com sucesso."
                : "Planta atualizada com sucesso.");
        redirect(request, response, "/plantas");
    }

    private void carregarOpcoes(HttpServletRequest request) {
        request.setAttribute("ambientes", ambienteService.listarTodos());
        request.setAttribute("usuarios", usuarioService.listarTodos());
    }
}
