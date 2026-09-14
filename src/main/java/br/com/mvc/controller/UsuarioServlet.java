package br.com.mvc.controller;

import java.io.IOException;
import java.util.List;

import br.com.mvc.model.Usuario;
import br.com.mvc.service.PerfilService;
import br.com.mvc.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends BaseServlet {

    private final UsuarioService usuarioService = new UsuarioService();
    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("novo".equals(acao)) {
            request.setAttribute("usuario", new Usuario());
            request.setAttribute("perfis", perfilService.listarTodos());
            forward(request, response, "/WEB-INF/jsp/usuarios/form.jsp");
            return;
        }

        if ("editar".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            Usuario usuario = usuarioService.buscarPorId(id);
            request.setAttribute("usuario", usuario);
            request.setAttribute("perfis", perfilService.listarTodos());
            forward(request, response, "/WEB-INF/jsp/usuarios/form.jsp");
            return;
        }

        if ("excluir".equals(acao)) {
            Long id = parseId(request.getParameter("id"));
            if (id != null) {
                usuarioService.excluir(id);
                request.getSession().setAttribute("mensagemSucesso", "Usuário excluído com sucesso.");
            }
            redirect(request, response, "/usuarios");
            return;
        }

        List<Usuario> usuarios = usuarioService.listarTodos();
        request.setAttribute("usuarios", usuarios);
        forward(request, response, "/WEB-INF/jsp/usuarios/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = parseId(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        Long perfilId = parseId(request.getParameter("perfilId"));

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setPerfilId(perfilId);

        List<String> erros = usuarioService.validar(usuario);
        if (!erros.isEmpty()) {
            request.setAttribute("usuario", usuario);
            request.setAttribute("perfis", perfilService.listarTodos());
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/usuarios/form.jsp");
            return;
        }

        if (usuario.getId() == null) {
            usuarioService.inserir(usuario);
            request.getSession().setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso.");
        } else {
            usuarioService.alterar(usuario);
            request.getSession().setAttribute("mensagemSucesso", "Usuário atualizado com sucesso.");
        }

        redirect(request, response, "/usuarios");
    }
}
