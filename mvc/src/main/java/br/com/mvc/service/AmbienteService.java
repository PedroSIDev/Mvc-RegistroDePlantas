package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.AmbienteDAO;
import br.com.mvc.model.Ambiente;

public class AmbienteService {

    private final AmbienteDAO ambienteDAO;

    public AmbienteService() {
        this.ambienteDAO = new AmbienteDAO();
    }

    public List<String> validar(Ambiente ambiente) {
        List<String> erros = new ArrayList<>();

        if (ambiente == null) {
            erros.add("Ambiente inválido.");
            return erros;
        }

        ambiente.setNome(Validacao.texto(ambiente.getNome()));
        ambiente.setDescricao(Validacao.opcional(ambiente.getDescricao()));
        Validacao.tamanho(erros, ambiente.getNome(), 100, "Nome do ambiente");
        Validacao.tamanho(erros, ambiente.getDescricao(), 255, "Descrição");

        if (ambiente.getNome() == null || ambiente.getNome().trim().isEmpty()) {
            erros.add("Nome do ambiente é obrigatório.");
        }

        return erros;
    }

    public List<Ambiente> listarTodos() {
        return ambienteDAO.listarTodos();
    }

    public Ambiente buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return ambienteDAO.buscarPorId(id);
    }

    public void inserir(Ambiente ambiente) {
        Validacao.exigir(validar(ambiente));
        ambienteDAO.inserir(ambiente);
    }

    public void alterar(Ambiente ambiente) {
        Validacao.exigir(validar(ambiente));
        if (buscarPorId(ambiente.getId()) == null) {
            throw new IllegalArgumentException("Ambiente não encontrado. Atualize a lista e tente novamente.");
        }
        ambienteDAO.alterar(ambiente);
    }

    public void excluir(Long id) {
        ambienteDAO.deletar(id);
    }
}
