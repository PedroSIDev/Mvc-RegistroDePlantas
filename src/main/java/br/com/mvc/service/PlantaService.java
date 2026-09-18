package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.AmbienteDAO;
import br.com.mvc.dao.PlantaDAO;
import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.model.Planta;

public class PlantaService {

    private final PlantaDAO plantaDAO;
    private final UsuarioDAO usuarioDAO;
    private final AmbienteDAO ambienteDAO;

    public PlantaService() {
        this.plantaDAO = new PlantaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.ambienteDAO = new AmbienteDAO();
    }

    public List<String> validar(Planta planta) {
        List<String> erros = new ArrayList<>();

        if (planta == null) {
            erros.add("Planta inválida.");
            return erros;
        }

        planta.setNomePopular(Validacao.texto(planta.getNomePopular()));
        planta.setNomeCientifico(Validacao.opcional(planta.getNomeCientifico()));
        planta.setDataAquisicao(Validacao.opcional(planta.getDataAquisicao()));
        planta.setObservacoes(Validacao.opcional(planta.getObservacoes()));
        Validacao.tamanho(erros, planta.getNomePopular(), 150, "Nome popular");
        Validacao.tamanho(erros, planta.getNomeCientifico(), 150, "Nome científico");
        Validacao.textoLongo(erros, planta.getObservacoes(), "Observações / descrição");
        Validacao.data(erros, planta.getDataAquisicao(), "Data de aquisição");

        if (planta.getNomePopular() == null || planta.getNomePopular().trim().isEmpty()) {
            erros.add("Nome popular da planta é obrigatório.");
        }

        if (planta.getUsuarioId() == null) {
            erros.add("Usuário é obrigatório.");
        } else if (usuarioDAO.buscarPorId(planta.getUsuarioId()) == null) {
            erros.add("Usuário informado não existe.");
        }

        if (planta.getAmbienteId() == null) {
            erros.add("Ambiente é obrigatório.");
        } else if (ambienteDAO.buscarPorId(planta.getAmbienteId()) == null) {
            erros.add("Ambiente informado não existe.");
        }

        return erros;
    }

    public List<Planta> listarTodos() {
        return plantaDAO.listarTodos();
    }

    public Planta buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return plantaDAO.buscarPorId(id);
    }

    public void inserir(Planta planta) {
        Validacao.exigir(validar(planta));
        plantaDAO.inserir(planta);
    }

    public void alterar(Planta planta) {
        Validacao.exigir(validar(planta));
        if (buscarPorId(planta.getId()) == null) {
            throw new IllegalArgumentException("Planta não encontrada. Atualize a lista e tente novamente.");
        }
        plantaDAO.alterar(planta);
    }

    public void excluir(Long id) {
        plantaDAO.deletar(id);
    }
}
