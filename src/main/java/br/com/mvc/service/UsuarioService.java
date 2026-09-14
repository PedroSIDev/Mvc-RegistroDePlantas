package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<String> validar(Usuario usuario) {
        List<String> erros = new ArrayList<>();

        if (usuario == null) {
            erros.add("Usuário inválido.");
            return erros;
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            erros.add("Nome do usuário é obrigatório.");
        }

        if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            erros.add("Login é obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            erros.add("Senha é obrigatória.");
        }

        if (usuario.getPerfilId() == null) {
            erros.add("Perfil é obrigatório.");
        }

        return erros;
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return usuarioDAO.buscarPorId(id);
    }

    public Usuario autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorLogin(login.trim());
        if (usuario == null) {
            return null;
        }

        if (usuario.getSenha() != null && usuario.getSenha().equals(senha.trim())) {
            return usuario;
        }

        return null;
    }

    public void inserir(Usuario usuario) {
        usuarioDAO.inserir(usuario);
    }

    public void alterar(Usuario usuario) {
        usuarioDAO.alterar(usuario);
    }

    public void excluir(Long id) {
        if (id != null) {
            usuarioDAO.deletar(id);
        }
    }
}
