package br.com.mvc.dto;

import java.util.Set;

import br.com.mvc.controller.api.ApiException;
import br.com.mvc.model.Planta;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class PlantaEntrada {
    private static final Set<String> CAMPOS = Set.of("nomePopular", "nomeCientifico",
            "dataAquisicao", "observacoes", "usuarioId", "ambienteId");

    private PlantaEntrada() { }

    public static Planta fromJson(JsonObject json) {
        for (String campo : json.keySet()) {
            if (!CAMPOS.contains(campo)) {
                throw new ApiException(400, "CAMPO_DESCONHECIDO", "Campo não permitido: " + campo
                        + ". O ID é definido pela URL ou gerado no cadastro.");
            }
        }
        Planta planta = new Planta();
        planta.setNomePopular(texto(json, "nomePopular"));
        planta.setNomeCientifico(texto(json, "nomeCientifico"));
        planta.setDataAquisicao(texto(json, "dataAquisicao"));
        planta.setObservacoes(texto(json, "observacoes"));
        planta.setUsuarioId(id(json, "usuarioId"));
        planta.setAmbienteId(id(json, "ambienteId"));
        return planta;
    }

    private static String texto(JsonObject json, String campo) {
        JsonElement valor = json.get(campo);
        if (valor == null || valor.isJsonNull()) {
            return null;
        }
        if (!valor.isJsonPrimitive() || !valor.getAsJsonPrimitive().isString()) {
            throw new ApiException(400, "TIPO_INVALIDO", campo + " deve ser texto ou null.");
        }
        return valor.getAsString();
    }

    private static Long id(JsonObject json, String campo) {
        JsonElement valor = json.get(campo);
        if (valor == null || valor.isJsonNull()) {
            return null;
        }
        if (valor.isJsonPrimitive()) {
            JsonPrimitive numero = valor.getAsJsonPrimitive();
            if (numero.isNumber() && numero.getAsString().matches("[1-9][0-9]*")) {
                try {
                    return Long.valueOf(numero.getAsString());
                } catch (NumberFormatException e) {
                }
            }
        }
        throw new ApiException(400, "TIPO_INVALIDO", campo + " deve ser um número inteiro positivo no intervalo de Long.");
    }
}

