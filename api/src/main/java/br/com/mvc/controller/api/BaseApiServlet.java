package br.com.mvc.controller.api;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseApiServlet extends HttpServlet {
    protected static final String METODOS = "GET, HEAD, POST, PUT, DELETE, OPTIONS";

    @Override
    protected final void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store");
        try {
            switch (request.getMethod()) {
                case "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" -> super.service(request, response);
                default -> {
                    response.setHeader("Allow", metodosPermitidos(request));
                    throw new ApiException(405, "METODO_NAO_PERMITIDO", "Método HTTP não suportado por este recurso.");
                }
            }
        } catch (ApiException e) {
            ApiJson.erro(response, e);
        } catch (RuntimeException e) {
            log("Falha ao processar requisição da API.", e);
            ApiJson.erro(response, erroInterno(e));
        }
    }

    protected ApiException erroInterno(RuntimeException erro) {
        for (Throwable causa = erro; causa != null; causa = causa.getCause()) {
            if (causa instanceof SQLException sql) {
                return switch (sql.getErrorCode()) {
                    case 1451 -> new ApiException(409, "REGISTRO_VINCULADO", "A operação foi bloqueada porque existem registros vinculados.");
                    case 1452 -> new ApiException(422, "VINCULO_INVALIDO", "O usuário ou ambiente informado não existe mais.");
                    case 1062 -> new ApiException(409, "REGISTRO_DUPLICADO", "Já existe um registro com esses dados.");
                    case 1205, 1213 -> new ApiException(409, "CONFLITO_CONCORRENTE", "O registro está em uso por outra operação. Tente novamente.");
                    default -> new ApiException(500, "ERRO_INTERNO", "Não foi possível concluir a operação.");
                };
            }
        }
        return new ApiException(500, "ERRO_INTERNO", "Não foi possível concluir a operação.");
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Allow", metodosPermitidos(request));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    protected String metodosPermitidos(HttpServletRequest request) {
        return METODOS;
    }
}

