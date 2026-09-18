package br.com.mvc.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.dao.PerfilDAO;
import br.com.mvc.model.Perfil;
import br.com.mvc.model.Usuario;
import br.com.mvc.security.PasswordUtil;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final PerfilDAO perfilDAO = new PerfilDAO();

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<String> validar(Usuario usuario) {
        List<String> erros = new ArrayList<>();

        if (usuario == null) {
            erros.add("Usuário inválido.");
            return erros;
        }

        usuario.setNome(Validacao.texto(usuario.getNome()));
        usuario.setLogin(Validacao.texto(usuario.getLogin()));
        Validacao.tamanho(erros, usuario.getNome(), 150, "Nome do usuário");
        Validacao.tamanho(erros, usuario.getLogin(), 100, "Login");
        if (usuario.getSenha() != null
                && usuario.getSenha().trim().getBytes(StandardCharsets.UTF_8).length > 72) {
            erros.add("A senha é muito longa. Use no máximo 72 bytes (acentos podem ocupar mais de um byte).");
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
        } else {
            Perfil perfil = perfilDAO.buscarPorId(usuario.getPerfilId());
            if (perfil == null) {
                erros.add("O perfil selecionado não existe mais. Selecione outro perfil.");
            } else if (usuario.getId() != null) {
                Usuario atual = buscarPorId(usuario.getId());
                if (atual != null && atual.getPerfil() != null
                        && "Administrador".equalsIgnoreCase(atual.getPerfil().getNome())
                        && !"Administrador".equalsIgnoreCase(perfil.getNome())
                        && listarTodos().stream().filter(u -> u.getPerfil() != null
                            && "Administrador".equalsIgnoreCase(u.getPerfil().getNome())).count() <= 1) {
                    erros.add("Não é permitido alterar o perfil do único administrador do sistema.");
                }
            }
        }

        return erros;
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {
        if (id == null || id <= 0) {
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
        Validacao.exigir(validar(usuario));
        if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
            usuario.setSenha(PasswordUtil.hash(usuario.getSenha().trim()));
        }
        usuarioDAO.inserir(usuario);
    }

    public void alterar(Usuario usuario) {
        Validacao.exigir(validar(usuario));
        if (buscarPorId(usuario.getId()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado. Atualize a lista e tente novamente.");
        }
        if (usuario.getId() != null) {
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                Usuario usuarioAtual = usuarioDAO.buscarPorId(usuario.getId());
                if (usuarioAtual != null) {
                    usuario.setSenha(usuarioAtual.getSenha());
                }
            } else {
                usuario.setSenha(PasswordUtil.hash(usuario.getSenha().trim()));
            }
        }
        usuarioDAO.alterar(usuario);
    }

    public void excluir(Long id, Usuario usuarioLogado) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Selecione um usuário válido para excluir.");
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
