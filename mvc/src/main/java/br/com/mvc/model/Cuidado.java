package br.com.mvc.model;

public class Cuidado {

    private Long id;
    private String nome;
    private String descricao;
    private int diasIntervalo;

    public Cuidado() {
    }

    public Cuidado(Long id, String nome, String descricao, int diasIntervalo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.diasIntervalo = diasIntervalo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDiasIntervalo() {
        return diasIntervalo;
    }

    public void setDiasIntervalo(int diasIntervalo) {
        this.diasIntervalo = diasIntervalo;
    }
}
