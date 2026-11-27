package br.com.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Ambiente;

public class AmbienteDAO extends MysqlDAO {

    public AmbienteDAO() {
        super();
    }

    public List<Ambiente> listarTodos() {
        String sql = "SELECT id, nome, descricao FROM ambientes ORDER BY nome";
        List<Ambiente> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ambientes.", e);
        }
        return lista;
    }

    public Ambiente buscarPorId(Long id) {
        String sql = "SELECT id, nome, descricao FROM ambientes WHERE id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ambiente.", e);
        }
        return null;
    }

    public void inserir(Ambiente ambiente) {
        String sql = "INSERT INTO ambientes (nome, descricao) VALUES (?, ?)";
        try {
            super.executarUpdate(sql, ambiente.getNome(), ambiente.getDescricao());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ambiente.", e);
        }
    }

    public void alterar(Ambiente ambiente) {
        String sql = "UPDATE ambientes SET nome = ?, descricao = ? WHERE id = ?";
        try {
            super.alterarExistente(sql, ambiente.getNome(), ambiente.getDescricao(), ambiente.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar ambiente.", e);
        }
    }

    public void deletar(Long id) {
        excluirSemVinculos(id,
                "SELECT id FROM ambientes WHERE id = ? FOR UPDATE",
                "SELECT id FROM plantas WHERE ambiente_id = ? LIMIT 1 FOR UPDATE",
                "DELETE FROM ambientes WHERE id = ?",
                "Não é possível excluir este ambiente: existem plantas vinculadas. Transfira ou exclua essas plantas primeiro.");
    }

    private Ambiente mapear(ResultSet rs) throws SQLException {
        Ambiente ambiente = new Ambiente();
        ambiente.setId(rs.getLong("id"));
        ambiente.setNome(rs.getString("nome"));
        ambiente.setDescricao(rs.getString("descricao"));
        return ambiente;
    }
}
