package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.PerfilDAO;
import br.com.mvc.model.Perfil;

public class PerfilService {

    private final PerfilDAO perfilDAO;

    public PerfilService() {
        this.perfilDAO = new PerfilDAO();
    }

    public List<String> validar(Perfil perfil) {
        List<String> erros = new ArrayList<>();

        if (perfil == null) {
            erros.add("Perfil inválido.");
            return erros;
        }

        if (perfil.getNome() == null || perfil.getNome().trim().isEmpty()) {
            erros.add("Nome do perfil é obrigatório.");
        }

        return erros;
    }

    public List<Perfil> listarTodos() {
        return perfilDAO.listarTodos();
    }

    public Perfil buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return perfilDAO.buscarPorId(id);
    }

    public void inserir(Perfil perfil) {
        perfilDAO.inserir(perfil);
    }

    public void alterar(Perfil perfil) {
        perfilDAO.alterar(perfil);
    }

    public void excluir(Long id) {
        if (id != null) {
            perfilDAO.deletar(id);
        }
    }
}
