package br.com.mvc.controller;

import java.io.IOException;
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

        if ("novo".equals(acao)) {
            request.setAttribute("cuidado", new Cuidado());
            forward(request, response, "/WEB-INF/jsp/cuidados/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Cuidado cuidado = cuidadoService.buscarPorId(id);
            if (cuidado == null) {
                request.setAttribute("mensagemErro", "Cuidado não encontrado.");
            }
            request.setAttribute("cuidado", cuidado);
            forward(request, response, "/WEB-INF/jsp/cuidados/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                cuidadoService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Cuidado excluído com sucesso.");
            }
            redirect(request, response, "/cuidados");
            return;
        }

        List<Cuidado> cuidados = cuidadoService.listarTodos();
        request.setAttribute("cuidados", cuidados);
        forward(request, response, "/WEB-INF/jsp/cuidados/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String diasIntervaloParam = request.getParameter("diasIntervalo");

        Cuidado cuidado = new Cuidado();
        cuidado.setId(id);
        cuidado.setNome(nome);
        cuidado.setDescricao(descricao);

        if (diasIntervaloParam != null && !diasIntervaloParam.trim().isEmpty()) {
            try {
                cuidado.setDiasIntervalo(Integer.parseInt(diasIntervaloParam));
            } catch (NumberFormatException e) {
                cuidado.setDiasIntervalo(0);
            }
        }

        List<String> erros = cuidadoService.validar(cuidado);
        if (!erros.isEmpty()) {
            request.setAttribute("cuidado", cuidado);
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/cuidados/form.jsp");
            return;
        }

        if (cuidado.getId() == null) {
            cuidadoService.inserir(cuidado);
            request.getSession().setAttribute("mensagemSucesso", "Cuidado cadastrado com sucesso.");
        } else {
            cuidadoService.alterar(cuidado);
            request.getSession().setAttribute("mensagemSucesso", "Cuidado atualizado com sucesso.");
        }

        redirect(request, response, "/cuidados");
    }
}
