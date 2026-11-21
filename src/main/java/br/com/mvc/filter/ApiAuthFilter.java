package br.com.mvc.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import br.com.mvc.controller.api.ApiException;
import br.com.mvc.controller.api.ApiJson;
import br.com.mvc.model.Usuario;
import br.com.mvc.service.UsuarioService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@WebFilter(urlPatterns = "/api/*")
public class ApiAuthFilter implements Filter {
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = "HEAD".equals(req.getMethod())
                ? new HttpServletResponseWrapper((HttpServletResponse) response) {
                    @Override
                    public PrintWriter getWriter() {
                        return new PrintWriter(Writer.nullWriter());
                    }
                } : (HttpServletResponse) response;
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("X-Content-Type-Options", "nosniff");
        try {
            String[] credenciais = credenciais(req.getHeader("Authorization"));
            Usuario usuario = usuarioService.autenticar(credenciais[0], credenciais[1]);
            if (usuario == null) {
                throw naoAutorizado();
            }
        } catch (ApiException e) {
            res.setHeader("WWW-Authenticate", "Basic realm=\"Plantas API\", charset=\"UTF-8\"");
            ApiJson.erro(res, e);
            return;
        } catch (RuntimeException e) {
            req.getServletContext().log("Falha ao autenticar requisição da API.", e);
            ApiJson.erro(res, new ApiException(500, "ERRO_INTERNO", "Não foi possível autenticar a requisição."));
            return;
        }
        chain.doFilter(request, res);
    }

    private String[] credenciais(String header) {
        if (header == null || header.length() > 4096) {
            throw naoAutorizado();
        }
        String[] partes = header.trim().split("\\s+", 2);
        if (partes.length != 2 || !"Basic".equalsIgnoreCase(partes[0])) {
            throw naoAutorizado();
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(partes[1]);
            String texto = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes)).toString();
            int separador = texto.indexOf(':');
            if (separador <= 0 || separador == texto.length() - 1) {
                throw naoAutorizado();
            }
            return new String[] { texto.substring(0, separador), texto.substring(separador + 1) };
        } catch (IllegalArgumentException | CharacterCodingException e) {
            throw naoAutorizado();
        }
    }

    private ApiException naoAutorizado() {
        return new ApiException(401, "NAO_AUTORIZADO", "Informe login e senha válidos por HTTP Basic.");
    }
}

