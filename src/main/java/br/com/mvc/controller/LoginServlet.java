package br.com.mvc.controller;

import java.io.IOException;

import br.com.mvc.model.Usuario;
import br.com.mvc.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession(false) != null && request.getSession(false).getAttribute("usuarioLogado") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Usuario usuario = usuarioService.autenticar(login, senha);
        if (usuario == null) {
            request.setAttribute("erroLogin", "Login ou senha inválidos.");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("usuarioLogado", usuario);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
