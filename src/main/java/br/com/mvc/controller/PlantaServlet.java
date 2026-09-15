package br.com.mvc.controller;

import java.io.IOException;
import java.util.List;

import br.com.mvc.model.Planta;
import br.com.mvc.service.AmbienteService;
import br.com.mvc.service.PlantaService;
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

        if ("novo".equals(acao)) {
            request.setAttribute("plantas", plantaService.listarTodos());
            request.setAttribute("ambientes", ambienteService.listarTodos());
            request.setAttribute("usuarios", usuarioService.listarTodos());
            request.setAttribute("planta", new Planta());
            forward(request, response, "/WEB-INF/jsp/plantas/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Planta planta = plantaService.buscarPorId(id);
            request.setAttribute("plantas", plantaService.listarTodos());
            request.setAttribute("ambientes", ambienteService.listarTodos());
            request.setAttribute("usuarios", usuarioService.listarTodos());
            request.setAttribute("planta", planta);
            forward(request, response, "/WEB-INF/jsp/plantas/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                plantaService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Planta excluída com sucesso.");
            }
            redirect(request, response, "/plantas");
            return;
        }

        List<Planta> plantas = plantaService.listarTodos();
        request.setAttribute("plantas", plantas);
        forward(request, response, "/WEB-INF/jsp/plantas/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        String nomePopular = request.getParameter("nomePopular");
        String nomeCientifico = request.getParameter("nomeCientifico");
        String dataAquisicao = request.getParameter("dataAquisicao");
        String observacoes = request.getParameter("observacoes");
        Long usuarioId = parseId(request.getParameter("usuarioId"));
        Long ambienteId = parseId(request.getParameter("ambienteId"));

        Planta planta = new Planta();
        planta.setId(id);
        planta.setNomePopular(nomePopular);
        planta.setNomeCientifico(nomeCientifico);
        planta.setDataAquisicao(dataAquisicao);
        planta.setObservacoes(observacoes);
        planta.setUsuarioId(usuarioId);
        planta.setAmbienteId(ambienteId);

        List<String> erros = plantaService.validar(planta);
        if (!erros.isEmpty()) {
            request.setAttribute("planta", planta);
            request.setAttribute("ambientes", ambienteService.listarTodos());
            request.setAttribute("usuarios", usuarioService.listarTodos());
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/plantas/form.jsp");
            return;
        }

        if (planta.getId() == null) {
            plantaService.inserir(planta);
            request.getSession().setAttribute("mensagemSucesso", "Planta cadastrada com sucesso.");
        } else {
            plantaService.alterar(planta);
            request.getSession().setAttribute("mensagemSucesso", "Planta atualizada com sucesso.");
        }

        redirect(request, response, "/plantas");
    }
}
