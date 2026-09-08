package br.com.mvc.model;

public class Planta {
    private Long id;
    private String nomePopular;
    private String nomeCientifico;
    private String dataAquisicao;
    private String observacoes;
    private Long usuarioId;
    private Long ambienteId;
    private Usuario usuario;
    private Ambiente ambiente;

    public Planta() {
    }

    public Planta(Long id, String nomePopular, String nomeCientifico, String dataAquisicao, String observacoes,
            Long usuarioId, Long ambienteId, Usuario usuario, Ambiente ambiente) {
        this.id = id;
        this.nomePopular = nomePopular;
        this.nomeCientifico = nomeCientifico;
        this.dataAquisicao = dataAquisicao;
        this.observacoes = observacoes;
        this.usuarioId = usuarioId;
        this.ambienteId = ambienteId;
        this.usuario = usuario;
        this.ambiente = ambiente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePopular() {
        return nomePopular;
    }

    public void setNomePopular(String nomePopular) {
        this.nomePopular = nomePopular;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(String dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAmbienteId() {
        return ambienteId;
    }

    public void setAmbienteId(Long ambienteId) {
        this.ambienteId = ambienteId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
}
