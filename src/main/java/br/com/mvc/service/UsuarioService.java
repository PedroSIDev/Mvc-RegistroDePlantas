package br.com.mvc.service;

import java.util.List;

import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
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
}
