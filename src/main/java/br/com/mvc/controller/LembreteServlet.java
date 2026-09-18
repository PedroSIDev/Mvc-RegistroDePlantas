package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Lembrete;
import br.com.mvc.service.LembreteService;
import br.com.mvc.service.PlantaService;
import br.com.mvc.service.CuidadoService;
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
        if (acao != null && !acao.isBlank()) {
            try {
                if ("excluir".equals(acao)) {
                    throw new IllegalArgumentException("Para excluir, use o botão de exclusão e confirme a operação.");
                }
                if (!"novo".equals(acao) && !"editar".equals(acao)) {
                    throw new IllegalArgumentException("Ação inválida. Selecione uma opção da lista.");
                }
                Lembrete lembrete = "novo".equals(acao) ? new Lembrete()
                        : lembreteService.buscarPorId(idObrigatorio(request));
                if (lembrete == null) {
                    throw new IllegalArgumentException("Lembrete não encontrado. O registro pode ter sido excluído.");
                }
                request.setAttribute("lembrete", lembrete);
                carregarOpcoes(request);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/lembretes");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/lembretes/form.jsp");
            return;
        }

        try {
            request.setAttribute("lembretes", lembreteService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/lembretes/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                lembreteService.excluir(idObrigatorio(request));
                mensagem(request, true, "Lembrete excluído com sucesso.");
                redirect(request, response, "/lembretes");
                return;
            }
            id = idFormulario(request);
            if (id != null && lembreteService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Lembrete não encontrado. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/lembretes");
            return;
        }

        Lembrete lembrete = new Lembrete();
        lembrete.setId(id);
        lembrete.setPlantaId(parseId(request.getParameter("plantaId")));
        lembrete.setCuidadoId(parseId(request.getParameter("cuidadoId")));
        lembrete.setDataAgendada(request.getParameter("dataAgendada"));
        lembrete.setDataRealizada(request.getParameter("dataRealizada"));
        lembrete.setStatus(request.getParameter("status"));
        lembrete.setObservacao(request.getParameter("observacao"));
        request.setAttribute("lembrete", lembrete);

        List<String> erros = new ArrayList<>();
        try {
            carregarOpcoes(request);
            erros.addAll(lembreteService.validar(lembrete));
            if (erros.isEmpty()) {
                if (id == null) {
                    lembreteService.inserir(lembrete);
                } else {
                    lembreteService.alterar(lembrete);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/lembretes/form.jsp");
            return;
        }

        mensagem(request, true, id == null ? "Lembrete cadastrado com sucesso."
                : "Lembrete atualizado com sucesso.");
        redirect(request, response, "/lembretes");
    }

    private void carregarOpcoes(HttpServletRequest request) {
        request.setAttribute("plantas", plantaService.listarTodos());
        request.setAttribute("cuidados", cuidadoService.listarTodos());
    }
}
