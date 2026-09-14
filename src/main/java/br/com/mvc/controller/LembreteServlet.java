package br.com.mvc.controller;

import java.io.IOException;
import java.util.List;

import br.com.mvc.model.Cuidado;
import br.com.mvc.model.Lembrete;
import br.com.mvc.model.Planta;
import br.com.mvc.service.CuidadoService;
import br.com.mvc.service.LembreteService;
import br.com.mvc.service.PlantaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lembretes")
public class LembreteServlet extends BaseServlet {

    private final LembreteService lembreteService = new LembreteService();
    private final PlantaService plantaService = new PlantaService();
    private final CuidadoService cuidadoService = new CuidadoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("novo".equals(acao)) {
            request.setAttribute("lembrete", new Lembrete());
            request.setAttribute("plantas", plantaService.listarTodos());
            request.setAttribute("cuidados", cuidadoService.listarTodos());
            forward(request, response, "/WEB-INF/jsp/lembretes/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Lembrete lembrete = lembreteService.buscarPorId(id);
            request.setAttribute("lembrete", lembrete);
            request.setAttribute("plantas", plantaService.listarTodos());
            request.setAttribute("cuidados", cuidadoService.listarTodos());
            forward(request, response, "/WEB-INF/jsp/lembretes/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                lembreteService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Lembrete excluído com sucesso.");
            }
            redirect(request, response, "/lembretes");
            return;
        }

        List<Lembrete> lembretes = lembreteService.listarTodos();
        request.setAttribute("lembretes", lembretes);
        forward(request, response, "/WEB-INF/jsp/lembretes/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        Long plantaId = parseId(request.getParameter("plantaId"));
        Long cuidadoId = parseId(request.getParameter("cuidadoId"));
        String dataAgendada = request.getParameter("dataAgendada");
        String dataRealizada = request.getParameter("dataRealizada");
        String status = request.getParameter("status");
        String observacao = request.getParameter("observacao");

        Lembrete lembrete = new Lembrete();
        lembrete.setId(id);
        lembrete.setPlantaId(plantaId);
        lembrete.setCuidadoId(cuidadoId);
        lembrete.setDataAgendada(dataAgendada);
        lembrete.setDataRealizada(dataRealizada);
        lembrete.setStatus(status);
        lembrete.setObservacao(observacao);

        List<String> erros = lembreteService.validar(lembrete);
        if (!erros.isEmpty()) {
            request.setAttribute("lembrete", lembrete);
            request.setAttribute("plantas", plantaService.listarTodos());
            request.setAttribute("cuidados", cuidadoService.listarTodos());
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/lembretes/form.jsp");
            return;
        }

        if (lembrete.getId() == null) {
            lembreteService.inserir(lembrete);
            request.getSession().setAttribute("mensagemSucesso", "Lembrete cadastrado com sucesso.");
        } else {
            lembreteService.alterar(lembrete);
            request.getSession().setAttribute("mensagemSucesso", "Lembrete atualizado com sucesso.");
        }

        redirect(request, response, "/lembretes");
    }
}
