package br.com.mvc.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);
        boolean autenticado = session != null && session.getAttribute("usuarioLogado") != null;
        boolean paginaPublica = uri.endsWith("/login") || uri.endsWith("/login/") || uri.endsWith("/logout")
                || uri.endsWith("/logout/") || uri.endsWith("/css/") || uri.contains("/css/")
                || uri.endsWith("/index.jsp") || uri.endsWith("/mvc/") || uri.endsWith("/mvc");

        if (autenticado || paginaPublica) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
    }

    @Override
    public void destroy() {
    }
}
