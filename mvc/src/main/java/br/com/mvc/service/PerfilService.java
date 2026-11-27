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

        perfil.setNome(Validacao.texto(perfil.getNome()));
        Validacao.tamanho(erros, perfil.getNome(), 100, "Nome do perfil");
        if (perfil.getId() != null) {
            Perfil atual = buscarPorId(perfil.getId());
            if (atual != null && "Administrador".equalsIgnoreCase(atual.getNome())
                    && !"Administrador".equals(perfil.getNome())) {
                erros.add("O nome do perfil Administrador é reservado e não pode ser alterado.");
            }
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
        if (id == null || id <= 0) {
            return null;
        }
        return perfilDAO.buscarPorId(id);
    }

    public void inserir(Perfil perfil) {
        Validacao.exigir(validar(perfil));
        perfilDAO.inserir(perfil);
    }

    public void alterar(Perfil perfil) {
        Validacao.exigir(validar(perfil));
        if (buscarPorId(perfil.getId()) == null) {
            throw new IllegalArgumentException("Perfil não encontrado. Atualize a lista e tente novamente.");
        }
        perfilDAO.alterar(perfil);
    }

    public void excluir(Long id) {
        perfilDAO.deletar(id);
    }
}
