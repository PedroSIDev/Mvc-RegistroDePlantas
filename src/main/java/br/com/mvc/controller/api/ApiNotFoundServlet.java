package br.com.mvc.controller.api;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/*")
public class ApiNotFoundServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiJson.erro(response, new ApiException(404, "ROTA_NAO_ENCONTRADA", "Rota não encontrada."));
    }
}
