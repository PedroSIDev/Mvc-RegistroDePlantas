package br.com.mvc.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {

    protected void forward(HttpServletRequest request, HttpServletResponse response, String destino)
            throws ServletException, IOException {
        request.getRequestDispatcher(destino).forward(request, response);
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response, String destino)
            throws IOException {
        response.sendRedirect(request.getContextPath() + destino);
    }

    protected Long parseId(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
