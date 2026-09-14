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
        if (id == null) {
            return null;
        }
        return plantaDAO.buscarPorId(id);
    }

    public void inserir(Planta planta) {
        plantaDAO.inserir(planta);
    }

    public void alterar(Planta planta) {
        plantaDAO.alterar(planta);
    }

    public void excluir(Long id) {
        if (id != null) {
            plantaDAO.deletar(id);
        }
    }
}
