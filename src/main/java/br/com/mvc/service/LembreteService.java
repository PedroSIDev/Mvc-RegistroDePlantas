package br.com.mvc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.mvc.dao.CuidadoDAO;
import br.com.mvc.dao.LembreteDAO;
import br.com.mvc.dao.PlantaDAO;
import br.com.mvc.model.Lembrete;

public class LembreteService {

    private final LembreteDAO lembreteDAO;
    private final PlantaDAO plantaDAO;
    private final CuidadoDAO cuidadoDAO;

    public LembreteService() {
        this.lembreteDAO = new LembreteDAO();
        this.plantaDAO = new PlantaDAO();
        this.cuidadoDAO = new CuidadoDAO();
    }

    public List<String> validar(Lembrete lembrete) {
        List<String> erros = new ArrayList<>();

        if (lembrete == null) {
            erros.add("Lembrete inválido.");
            return erros;
        }

        if (lembrete.getPlantaId() == null) {
            erros.add("Planta é obrigatória.");
        } else if (plantaDAO.buscarPorId(lembrete.getPlantaId()) == null) {
            erros.add("Planta informada não existe.");
        }

        if (lembrete.getCuidadoId() == null) {
            erros.add("Cuidado é obrigatório.");
        } else if (cuidadoDAO.buscarPorId(lembrete.getCuidadoId()) == null) {
            erros.add("Cuidado informado não existe.");
        }

        if (lembrete.getDataAgendada() == null || lembrete.getDataAgendada().trim().isEmpty()) {
            erros.add("Data agendada é obrigatória.");
        }

        if (lembrete.getStatus() != null && !lembrete.getStatus().trim().isEmpty()) {
            List<String> statusPermitidos = Arrays.asList("PENDENTE", "CONCLUIDO", "CANCELADO");
            if (!statusPermitidos.contains(lembrete.getStatus().trim().toUpperCase())) {
                erros.add("Status inválido. Use PENDENTE, CONCLUIDO ou CANCELADO.");
            }
        }

        return erros;
    }

    public List<Lembrete> listarTodos() {
        return lembreteDAO.listarTodos();
    }

    public Lembrete buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return lembreteDAO.buscarPorId(id);
    }

    public void inserir(Lembrete lembrete) {
        if (lembrete.getStatus() == null || lembrete.getStatus().trim().isEmpty()) {
            lembrete.setStatus("PENDENTE");
        }
        lembreteDAO.inserir(lembrete);
    }

    public void alterar(Lembrete lembrete) {
        if (lembrete.getStatus() == null || lembrete.getStatus().trim().isEmpty()) {
            lembrete.setStatus("PENDENTE");
        }
        lembreteDAO.alterar(lembrete);
    }

    public void excluir(Long id) {
        if (id != null) {
            lembreteDAO.deletar(id);
        }
    }
}
