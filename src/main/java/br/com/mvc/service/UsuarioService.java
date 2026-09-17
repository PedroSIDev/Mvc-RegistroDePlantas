package br.com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.model.Usuario;
import br.com.mvc.security.PasswordUtil;

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
        } else {
            Usuario usuarioExistente = usuarioDAO.buscarPorLogin(usuario.getLogin().trim());
            if (usuarioExistente != null && (usuario.getId() == null || !usuarioExistente.getId().equals(usuario.getId()))) {
                erros.add("O login informado já está em uso por outro usuário.");
            }
        }

        if (usuario.getId() == null) {
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                erros.add("Senha é obrigatória.");
            } else if (usuario.getSenha().trim().length() < 6) {
                erros.add("A senha deve conter no mínimo 6 caracteres.");
            }
        } else {
            if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
                if (usuario.getSenha().trim().length() < 6) {
                    erros.add("A nova senha deve conter no mínimo 6 caracteres.");
                }
            }
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

        if (usuario.getSenha() != null && PasswordUtil.verificar(senha.trim(), usuario.getSenha())) {
            if (!PasswordUtil.isHashed(usuario.getSenha())) {
                String novoHash = PasswordUtil.hash(senha.trim());
                usuario.setSenha(novoHash);
                usuarioDAO.alterar(usuario);
            }
            return usuario;
        }

        return null;
    }

    public void inserir(Usuario usuario) {
        if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
            usuario.setSenha(PasswordUtil.hash(usuario.getSenha().trim()));
        }
        usuarioDAO.inserir(usuario);
    }

    public void alterar(Usuario usuario) {
        if (usuario.getId() != null) {
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                Usuario usuarioAtual = usuarioDAO.buscarPorId(usuario.getId());
                if (usuarioAtual != null) {
                    usuario.setSenha(usuarioAtual.getSenha());
                }
            } else if (!PasswordUtil.isHashed(usuario.getSenha())) {
                usuario.setSenha(PasswordUtil.hash(usuario.getSenha().trim()));
            }
        }
        usuarioDAO.alterar(usuario);
    }

    public void excluir(Long id, Usuario usuarioLogado) {
        if (id == null) {
            return;
        }

        if (usuarioLogado != null && id.equals(usuarioLogado.getId())) {
            throw new IllegalArgumentException("Não é permitido excluir o usuário atualmente conectado.");
        }

        Usuario usuarioParaExcluir = usuarioDAO.buscarPorId(id);
        if (usuarioParaExcluir != null && usuarioParaExcluir.getPerfil() != null
                && "Administrador".equalsIgnoreCase(usuarioParaExcluir.getPerfil().getNome())) {
            long totalAdmins = listarTodos().stream()
                    .filter(u -> u.getPerfil() != null && "Administrador".equalsIgnoreCase(u.getPerfil().getNome()))
                    .count();
            if (totalAdmins <= 1) {
                throw new IllegalArgumentException("Não é permitido excluir o único administrador do sistema.");
            }
        }

        usuarioDAO.deletar(id);
    }

    public void excluir(Long id) {
        excluir(id, null);
    }
}
