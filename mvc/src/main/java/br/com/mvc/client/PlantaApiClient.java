package br.com.mvc.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import br.com.mvc.model.Planta;

/**
 * Cliente HTTP do MVC para a API REST de plantas (container "api").
 *
 * Todo o CRUD de plantas feito pelas telas passa por aqui: o MVC não acessa
 * a tabela de plantas para gravar nem consultar plantas, ele envia e recebe JSON.
 *
 * Configuração (variáveis de ambiente):
 *   API_BASE_URL  endereço da API. No Docker Compose: http://api:8080 (nome do serviço).
 *   API_USUARIO   login usado na autenticação HTTP Basic da API.
 *   API_SENHA     senha desse login.
 */
public class PlantaApiClient {

    private static final Logger LOG = Logger.getLogger(PlantaApiClient.class.getName());

    private static final String BASE_URL = semBarraFinal(
            System.getenv().getOrDefault("API_BASE_URL", "http://localhost:8081"));
    private static final String USUARIO = System.getenv().getOrDefault("API_USUARIO", "pedro");
    private static final String SENHA = System.getenv().getOrDefault("API_SENHA", "123456");
    private static final String RECURSO = "/api/plantas";

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private static final Duration TIMEOUT = Duration.ofSeconds(15);
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

    /** GET /api/plantas */
    public List<Planta> listarTodos() {
        HttpResponse<String> resposta = enviar(requisicao(RECURSO).GET().build());
        exigirSucesso(resposta);
        JsonArray array = lerJson(resposta).getAsJsonArray();
        List<Planta> plantas = new ArrayList<>();
        for (JsonElement item : array) {
            plantas.add(GSON.fromJson(item, Planta.class));
        }
        return plantas;
    }

    /** GET /api/plantas/{id} — retorna null quando a API responde 404. */
    public Planta buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        HttpResponse<String> resposta = enviar(requisicao(RECURSO + "/" + id).GET().build());
        if (resposta.statusCode() == 404) {
            return null;
        }
        exigirSucesso(resposta);
        return GSON.fromJson(lerJson(resposta), Planta.class);
    }

    /** POST /api/plantas — devolve a planta criada (com o id gerado pela API). */
    public Planta inserir(Planta planta) {
        HttpResponse<String> resposta = enviar(requisicao(RECURSO)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpo(planta), StandardCharsets.UTF_8))
                .build());
        exigirSucesso(resposta);
        Planta criada = GSON.fromJson(lerJson(resposta), Planta.class);
        planta.setId(criada.getId());
        return criada;
    }

    /** PUT /api/plantas/{id} */
    public Planta alterar(Planta planta) {
        HttpResponse<String> resposta = enviar(requisicao(RECURSO + "/" + planta.getId())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(corpo(planta), StandardCharsets.UTF_8))
                .build());
        exigirSucesso(resposta);
        return GSON.fromJson(lerJson(resposta), Planta.class);
    }

    /** DELETE /api/plantas/{id} */
    public void excluir(Long id) {
        HttpResponse<String> resposta = enviar(requisicao(RECURSO + "/" + id).DELETE().build());
        exigirSucesso(resposta);
    }

    // ------------------------------------------------------------------ apoio

    private HttpRequest.Builder requisicao(String caminho) {
        String credenciais = Base64.getEncoder()
                .encodeToString((USUARIO + ":" + SENHA).getBytes(StandardCharsets.UTF_8));
        return HttpRequest.newBuilder(URI.create(BASE_URL + caminho))
                .timeout(TIMEOUT)
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + credenciais);
    }

    private HttpResponse<String> enviar(HttpRequest requisicao) {
        try {
            HttpResponse<String> resposta = HTTP.send(requisicao,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            LOG.info("MVC -> API " + requisicao.method() + " " + requisicao.uri() + " -> HTTP " + resposta.statusCode());
            return resposta;
        } catch (IOException e) {
            LOG.warning("MVC -> API " + requisicao.method() + " " + requisicao.uri() + " falhou: " + e);
            throw indisponivel(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw indisponivel(e);
        }
    }

    private void exigirSucesso(HttpResponse<String> resposta) {
        int status = resposta.statusCode();
        if (status >= 200 && status < 300) {
            return;
        }
        String codigo = "ERRO_API";
        String mensagem = "A API de plantas respondeu com erro (HTTP " + status + ").";
        List<String> detalhes = new ArrayList<>();
        try {
            JsonObject erro = JsonParser.parseString(resposta.body()).getAsJsonObject();
            if (erro.has("codigo") && !erro.get("codigo").isJsonNull()) {
                codigo = erro.get("codigo").getAsString();
            }
            if (erro.has("mensagem") && !erro.get("mensagem").isJsonNull()) {
                mensagem = erro.get("mensagem").getAsString();
            }
            if (erro.has("detalhes") && erro.get("detalhes").isJsonArray()) {
                for (JsonElement detalhe : erro.getAsJsonArray("detalhes")) {
                    detalhes.add(detalhe.getAsString());
                }
            }
        } catch (RuntimeException e) {
            // corpo fora do formato esperado: mantem a mensagem generica
        }
        if (status == 401) {
            mensagem = "O MVC não foi autorizado pela API de plantas. Verifique API_USUARIO e API_SENHA.";
        }
        throw new ApiClientException(status, codigo, mensagem, detalhes);
    }

    private JsonElement lerJson(HttpResponse<String> resposta) {
        try {
            return JsonParser.parseString(resposta.body());
        } catch (JsonParseException e) {
            throw new ApiClientException(502, "RESPOSTA_INVALIDA",
                    "A API de plantas devolveu uma resposta inválida.", e);
        }
    }

    /** Monta o JSON aceito pela API (mesmos campos do PlantaEntrada). */
    private String corpo(Planta planta) {
        JsonObject json = new JsonObject();
        json.addProperty("nomePopular", planta.getNomePopular());
        json.addProperty("nomeCientifico", planta.getNomeCientifico());
        json.addProperty("dataAquisicao", planta.getDataAquisicao());
        json.addProperty("observacoes", planta.getObservacoes());
        json.addProperty("usuarioId", planta.getUsuarioId());
        json.addProperty("ambienteId", planta.getAmbienteId());
        return GSON.toJson(json);
    }

    private static ApiClientException indisponivel(Exception causa) {
        return new ApiClientException(503, "API_INDISPONIVEL",
                "O serviço de plantas (API) está indisponível no momento. Tente novamente em instantes.", causa);
    }

    private static String semBarraFinal(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
