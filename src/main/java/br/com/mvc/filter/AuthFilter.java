package br.com.mvc.filter;

import java.io.IOException;

import br.com.mvc.model.Usuario;
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

        // Padronização de encoding UTF-8
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");

        // Cabeçalhos de Segurança HTTP (Defesa em profundidade / OWASP)
        httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
        httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        String servletPath = httpRequest.getServletPath();
        if (servletPath == null) {
            servletPath = "";
        }

        // Recursos estáticos e páginas públicas
        boolean isRecursoEstatico = servletPath.startsWith("/css/")
                || servletPath.startsWith("/js/")
                || servletPath.startsWith("/images/")
                || servletPath.endsWith(".css")
                || servletPath.endsWith(".js")
                || servletPath.endsWith(".png")
                || servletPath.endsWith(".jpg")
                || servletPath.endsWith(".ico");

        boolean isPaginaPublica = servletPath.equals("")
                || servletPath.equals("/")
                || servletPath.equals("/index.jsp")
            || servletPath.equals("/landing")
                || servletPath.equals("/login")
                || servletPath.equals("/logout");

        if (isRecursoEstatico || isPaginaPublica) {
            chain.doFilter(request, response);
            return;
        }

        // Verificação de Autenticação
        HttpSession session = httpRequest.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuarioLogado == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Verificação de Autorização Baseada em Perfil (RBAC)
        // Apenas Administradores têm acesso ao gerenciamento de Usuários e Perfis
        boolean isRotaAdmin = servletPath.equals("/usuarios")
                || servletPath.startsWith("/usuarios/")
                || servletPath.equals("/perfis")
                || servletPath.startsWith("/perfis/");

        if (isRotaAdmin) {
            boolean isAdmin = usuarioLogado.getPerfil() != null
                    && "Administrador".equalsIgnoreCase(usuarioLogado.getPerfil().getNome());

            if (!isAdmin) {
                httpRequest.getSession().setAttribute("mensagemErro", "Acesso restrito: seu perfil não tem permissão para gerenciar usuários ou perfis.");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
