package br.com.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Perfil;
import br.com.mvc.model.Usuario;

public class UsuarioDAO extends MysqlDAO {

    public UsuarioDAO() {
        super();
    }

    public List<Usuario> listarTodos() {
        String sql = "SELECT u.id, u.nome, u.login, u.senha, u.perfil_id, p.id AS perfil_id_rel, p.nome AS perfil_nome "
                + "FROM usuarios u INNER JOIN perfis p ON p.id = u.perfil_id ORDER BY u.nome";
        List<Usuario> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários.", e);
        }
        return lista;
    }

    public Usuario buscarPorId(Long id) {
        String sql = "SELECT u.id, u.nome, u.login, u.senha, u.perfil_id, p.id AS perfil_id_rel, p.nome AS perfil_nome "
                + "FROM usuarios u INNER JOIN perfis p ON p.id = u.perfil_id WHERE u.id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário.", e);
        }
        return null;
    }

    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT u.id, u.nome, u.login, u.senha, u.perfil_id, p.id AS perfil_id_rel, p.nome AS perfil_nome "
                + "FROM usuarios u INNER JOIN perfis p ON p.id = u.perfil_id WHERE u.login = ?";
        try (ResultSet rs = super.executar(sql, login)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por login.", e);
        }
        return null;
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, login, senha, perfil_id) VALUES (?, ?, ?, ?)";
        try {
            super.executarUpdate(sql, usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getPerfilId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário.", e);
        }
    }

    public void alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, login = ?, senha = ?, perfil_id = ? WHERE id = ?";
        try {
            super.executarUpdate(sql, usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getPerfilId(), usuario.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar usuário.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário.", e);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setLogin(rs.getString("login"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPerfilId(rs.getLong("perfil_id"));

        Perfil perfil = new Perfil();
        perfil.setId(rs.getLong("perfil_id_rel"));
        perfil.setNome(rs.getString("perfil_nome"));
        usuario.setPerfil(perfil);

        return usuario;
    }
}
