package br.com.mvc.dto;

import br.com.mvc.model.Planta;

public record PlantaSaida(Long id, String nomePopular, String nomeCientifico,
        String dataAquisicao, String observacoes, Long usuarioId, Long ambienteId) {

    public static PlantaSaida from(Planta planta) {
        return new PlantaSaida(planta.getId(), planta.getNomePopular(), planta.getNomeCientifico(),
                planta.getDataAquisicao(), planta.getObservacoes(), planta.getUsuarioId(), planta.getAmbienteId());
    }

}

