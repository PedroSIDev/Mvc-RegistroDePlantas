package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.CuidadoDAO;
import br.com.mvc.model.Cuidado;

public class CuidadoService {

    private final CuidadoDAO cuidadoDAO;

    public CuidadoService() {
        this.cuidadoDAO = new CuidadoDAO();
    }

    public List<String> validar(Cuidado cuidado) {
        List<String> erros = new ArrayList<>();

        if (cuidado == null) {
            erros.add("Cuidado inválido.");
            return erros;
        }

        if (cuidado.getNome() == null || cuidado.getNome().trim().isEmpty()) {
            erros.add("Nome do cuidado é obrigatório.");
        }

        if (cuidado.getDiasIntervalo() <= 0) {
            erros.add("Intervalo em dias deve ser maior que zero.");
        }

        return erros;
    }

    public List<Cuidado> listarTodos() {
        return cuidadoDAO.listarTodos();
    }

    public Cuidado buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return cuidadoDAO.buscarPorId(id);
    }

    public void inserir(Cuidado cuidado) {
        cuidadoDAO.inserir(cuidado);
    }

    public void alterar(Cuidado cuidado) {
        cuidadoDAO.alterar(cuidado);
    }

    public void excluir(Long id) {
        if (id != null) {
            cuidadoDAO.deletar(id);
        }
    }
}
