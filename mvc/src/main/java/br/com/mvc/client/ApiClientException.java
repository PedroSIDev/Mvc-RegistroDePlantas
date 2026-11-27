package br.com.mvc.client;

import java.util.List;

/**
 * Erro devolvido pela API REST de plantas (ou falha de comunicação com ela).
 *
 * Carrega o mesmo formato de erro que a API responde em JSON:
 * {"status": 422, "codigo": "DADOS_INVALIDOS", "mensagem": "...", "detalhes": ["..."]}
 */
public class ApiClientException extends RuntimeException {

    private final int status;
    private final String codigo;
    private final List<String> detalhes;

    public ApiClientException(int status, String codigo, String mensagem, List<String> detalhes) {
        super(mensagem);
        this.status = status;
        this.codigo = codigo;
        this.detalhes = detalhes == null ? List.of() : List.copyOf(detalhes);
    }

    public ApiClientException(int status, String codigo, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.status = status;
        this.codigo = codigo;
        this.detalhes = List.of();
    }

    public int getStatus() {
        return status;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<String> getDetalhes() {
        return detalhes;
    }
}
