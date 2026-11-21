package br.com.mvc.controller.api;

import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class ApiJson {
    private static final int MAX_BODY_BYTES = 131072;
    private static final Gson GSON = new GsonBuilder()
            .setStrictness(Strictness.STRICT).serializeNulls().create();

    private ApiJson() { }

    public static JsonObject lerObjeto(HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();
        if (contentType == null || !"application/json".equals(
                contentType.split(";", 2)[0].trim().toLowerCase(Locale.ROOT))) {
            throw new ApiException(415, "TIPO_NAO_SUPORTADO", "Envie o corpo com Content-Type: application/json.");
        }
        String charset = request.getCharacterEncoding();
        if (charset != null && !"UTF-8".equalsIgnoreCase(charset)) {
            throw new ApiException(415, "CHARSET_NAO_SUPORTADO", "O JSON deve usar UTF-8.");
        }
        byte[] bytes = request.getInputStream().readNBytes(MAX_BODY_BYTES + 1);
        if (bytes.length > MAX_BODY_BYTES) {
            throw new ApiException(413, "CORPO_MUITO_GRANDE", "O JSON deve ter no máximo 128 KiB.");
        }
        String body;
        try {
            body = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            throw new ApiException(400, "JSON_INVALIDO", "O corpo contém caracteres UTF-8 inválidos.");
        }
        return objeto(body);
    }

    public static JsonObject objeto(String body) {
        if (body == null || body.isBlank()) {
            throw new ApiException(400, "JSON_INVALIDO", "Informe um objeto JSON no corpo da requisição.");
        }
        try (JsonReader reader = new JsonReader(new StringReader(body))) {
            reader.setStrictness(Strictness.STRICT);
            reader.beginObject();
            JsonObject result = new JsonObject();
            Set<String> campos = new HashSet<>();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (!campos.add(name)) {
                    throw new ApiException(400, "CAMPO_DUPLICADO", "O campo " + name + " aparece mais de uma vez.");
                }
                JsonElement value = JsonParser.parseReader(reader);
                result.add(name, value);
            }
            reader.endObject();
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw new ApiException(400, "JSON_INVALIDO", "Envie apenas um objeto JSON.");
            }
            return result;
        } catch (IOException | JsonParseException | IllegalStateException e) {
            throw new ApiException(400, "JSON_INVALIDO", "JSON malformado. Use um objeto com nomes e textos entre aspas duplas.");
        }
    }

    public static void escrever(HttpServletResponse response, int status, Object body) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        GSON.toJson(body, response.getWriter());
    }

    public static void erro(HttpServletResponse response, ApiException erro) throws IOException {
        escrever(response, erro.getStatus(),
                new Erro(erro.getStatus(), erro.getCodigo(), erro.getMessage(), erro.getDetalhes()));
    }

    private record Erro(int status, String codigo, String mensagem, List<String> detalhes) { }
}

