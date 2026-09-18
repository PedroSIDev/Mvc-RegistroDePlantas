package br.com.mvc.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class Validacao {
    private Validacao() { }

    public static String texto(String valor) {
        return valor == null ? null : valor.trim();
    }

    public static String opcional(String valor) {
        String texto = texto(valor);
        return texto == null || texto.isEmpty() ? null : texto;
    }

    public static void tamanho(List<String> erros, String valor, int limite, String campo) {
        if (valor != null && valor.codePointCount(0, valor.length()) > limite) {
            erros.add(campo + " deve ter no máximo " + limite + " caracteres.");
        }
    }

    public static void textoLongo(List<String> erros, String valor, String campo) {
        if (valor != null && valor.getBytes(StandardCharsets.UTF_8).length > 65535) {
            erros.add(campo + " excede o tamanho permitido. Reduza o texto.");
        }
    }

    public static LocalDate data(List<String> erros, String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            LocalDate data = LocalDate.parse(valor);
            if (!valor.matches("\\d{4}-\\d{2}-\\d{2}") || data.getYear() < 1000) {
                throw new DateTimeParseException("Data fora do intervalo permitido.", valor, 0);
            }
            return data;
        } catch (DateTimeParseException e) {
            erros.add(campo + " inválida. Informe uma data real entre os anos 1000 e 9999.");
            return null;
        }
    }

    public static void exigir(List<String> erros) {
        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", erros));
        }
    }
}
