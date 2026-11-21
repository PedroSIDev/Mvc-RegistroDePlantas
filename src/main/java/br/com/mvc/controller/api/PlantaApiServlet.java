package br.com.mvc.controller.api;

import java.io.IOException;
import java.util.List;

import br.com.mvc.dto.PlantaEntrada;
import br.com.mvc.dto.PlantaSaida;
import br.com.mvc.model.Planta;
import br.com.mvc.service.PlantaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/plantas/*")
public class PlantaApiServlet extends BaseApiServlet {
    private final PlantaService plantaService = new PlantaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = idRota(request);
        if (id == null) {
            ApiJson.escrever(response, 200, plantaService.listarTodos().stream().map(PlantaSaida::from).toList());
        } else {
            ApiJson.escrever(response, 200, PlantaSaida.from(buscar(id)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (idRota(request) != null) {
            metodoNaoPermitido(response, "GET, HEAD, PUT, DELETE, OPTIONS");
        }
        Planta planta = lerEValidar(request);
        try {
            plantaService.inserir(planta);
        } catch (IllegalArgumentException e) {
            throw new ApiException(422, "DADOS_INVALIDOS", e.getMessage());
        }
        response.setHeader("Location", request.getContextPath() + "/api/plantas/" + planta.getId());
        ApiJson.escrever(response, 201, PlantaSaida.from(planta));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = exigirId(request, response);
        buscar(id);
        Planta planta = lerEValidar(request);
        planta.setId(id);
        try {
            plantaService.alterar(planta);
        } catch (IllegalArgumentException e) {
            if (plantaService.buscarPorId(id) == null) {
                throw naoEncontrada();
            }
            throw new ApiException(422, "DADOS_INVALIDOS", e.getMessage());
        }
        ApiJson.escrever(response, 200, PlantaSaida.from(planta));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = exigirId(request, response);
        buscar(id);
        try {
            plantaService.excluir(id);
        } catch (IllegalArgumentException e) {
            if (plantaService.buscarPorId(id) == null) {
                throw naoEncontrada();
            }
            throw new ApiException(409, "REGISTRO_VINCULADO", e.getMessage());
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected String metodosPermitidos(HttpServletRequest request) {
        return idRota(request) == null
                ? "GET, HEAD, POST, OPTIONS" : "GET, HEAD, PUT, DELETE, OPTIONS";
    }

    private Planta lerEValidar(HttpServletRequest request) throws IOException {
        Planta planta = PlantaEntrada.fromJson(ApiJson.lerObjeto(request));
        List<String> erros = plantaService.validar(planta);
        if (!erros.isEmpty()) {
            throw new ApiException(422, "DADOS_INVALIDOS", "Revise os dados da planta.", erros);
        }
        return planta;
    }

    private Planta buscar(Long id) {
        Planta planta = plantaService.buscarPorId(id);
        if (planta == null) {
            throw naoEncontrada();
        }
        return planta;
    }

    private ApiException naoEncontrada() {
        return new ApiException(404, "PLANTA_NAO_ENCONTRADA", "Planta não encontrada.");
    }

    private Long exigirId(HttpServletRequest request, HttpServletResponse response) {
        Long id = idRota(request);
        if (id == null) {
            metodoNaoPermitido(response, "GET, HEAD, POST, OPTIONS");
        }
        return id;
    }

    private void metodoNaoPermitido(HttpServletResponse response, String allow) {
        response.setHeader("Allow", allow);
        throw new ApiException(405, "METODO_NAO_PERMITIDO", "Este método não é permitido nesta URL.");
    }

    private Long idRota(HttpServletRequest request) {
        String caminho = request.getPathInfo();
        if (caminho == null || caminho.isEmpty() || "/".equals(caminho)) {
            return null;
        }
        if (caminho.substring(1).contains("/")) {
            throw new ApiException(404, "ROTA_NAO_ENCONTRADA", "Rota não encontrada.");
        }
        String valor = caminho.substring(1);
        if (valor.matches("[1-9][0-9]*")) {
            try {
                return Long.valueOf(valor);
            } catch (NumberFormatException e) {
            }
        }
        throw new ApiException(400, "ID_INVALIDO", "O ID da URL deve ser um número inteiro positivo no intervalo de Long.");
    }
}

