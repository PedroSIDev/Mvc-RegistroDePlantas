package br.com.mvc.controller.api;

import java.util.List;

public class ApiException extends RuntimeException {
    private final int status;
    private final String codigo;
    private final List<String> detalhes;

    public ApiException(int status, String codigo, String mensagem) {
        this(status, codigo, mensagem, List.of());
    }

    public ApiException(int status, String codigo, String mensagem, List<String> detalhes) {
        super(mensagem);
        this.status = status;
        this.codigo = codigo;
        this.detalhes = List.copyOf(detalhes);
    }

    public int getStatus() { return status; }
    public String getCodigo() { return codigo; }
    public List<String> getDetalhes() { return detalhes; }
}

