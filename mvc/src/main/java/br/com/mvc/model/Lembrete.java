package br.com.mvc.model;

public class Lembrete {
    private Long id;
    private Long plantaId;
    private Long cuidadoId;
    private String dataAgendada;
    private String dataRealizada;
    private String status;
    private String observacao;
    private Planta planta;
    private Cuidado cuidado;

    public Lembrete() {
    }

    public Lembrete(Long id, Long plantaId, Long cuidadoId, String dataAgendada, String dataRealizada, String status,
            String observacao, Planta planta, Cuidado cuidado) {
        this.id = id;
        this.plantaId = plantaId;
        this.cuidadoId = cuidadoId;
        this.dataAgendada = dataAgendada;
        this.dataRealizada = dataRealizada;
        this.status = status;
        this.observacao = observacao;
        this.planta = planta;
        this.cuidado = cuidado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantaId() {
        return plantaId;
    }

    public void setPlantaId(Long plantaId) {
        this.plantaId = plantaId;
    }

    public Long getCuidadoId() {
        return cuidadoId;
    }

    public void setCuidadoId(Long cuidadoId) {
        this.cuidadoId = cuidadoId;
    }

    public String getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(String dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(String dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public Cuidado getCuidado() {
        return cuidado;
    }

    public void setCuidado(Cuidado cuidado) {
        this.cuidado = cuidado;
    }
}
