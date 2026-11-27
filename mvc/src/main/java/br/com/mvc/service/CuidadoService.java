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

        cuidado.setNome(Validacao.texto(cuidado.getNome()));
        cuidado.setDescricao(Validacao.opcional(cuidado.getDescricao()));
        Validacao.tamanho(erros, cuidado.getNome(), 100, "Nome do cuidado");
        Validacao.textoLongo(erros, cuidado.getDescricao(), "Observações / descrição");

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
        if (id == null || id <= 0) {
            return null;
        }
        return cuidadoDAO.buscarPorId(id);
    }

    public void inserir(Cuidado cuidado) {
        Validacao.exigir(validar(cuidado));
        cuidadoDAO.inserir(cuidado);
    }

    public void alterar(Cuidado cuidado) {
        Validacao.exigir(validar(cuidado));
        if (buscarPorId(cuidado.getId()) == null) {
            throw new IllegalArgumentException("Cuidado não encontrado. Atualize a lista e tente novamente.");
        }
        cuidadoDAO.alterar(cuidado);
    }

    public void excluir(Long id) {
        cuidadoDAO.deletar(id);
    }
}
