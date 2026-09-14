package br.com.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Cuidado;

public class CuidadoDAO extends MysqlDAO {

    public CuidadoDAO() {
        super();
    }

    public List<Cuidado> listarTodos() {
        String sql = "SELECT id, nome, descricao, dias_intervalo FROM cuidados ORDER BY nome";
        List<Cuidado> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cuidados.", e);
        }
        return lista;
    }

    public Cuidado buscarPorId(Long id) {
        String sql = "SELECT id, nome, descricao, dias_intervalo FROM cuidados WHERE id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cuidado.", e);
        }
        return null;
    }

    public void inserir(Cuidado cuidado) {
        String sql = "INSERT INTO cuidados (nome, descricao, dias_intervalo) VALUES (?, ?, ?)";
        try {
            super.executarUpdate(sql, cuidado.getNome(), cuidado.getDescricao(), cuidado.getDiasIntervalo());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir cuidado.", e);
        }
    }

    public void alterar(Cuidado cuidado) {
        String sql = "UPDATE cuidados SET nome = ?, descricao = ?, dias_intervalo = ? WHERE id = ?";
        try {
            super.executarUpdate(sql, cuidado.getNome(), cuidado.getDescricao(), cuidado.getDiasIntervalo(), cuidado.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar cuidado.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM cuidados WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cuidado.", e);
        }
    }

    private Cuidado mapear(ResultSet rs) throws SQLException {
        Cuidado cuidado = new Cuidado();
        cuidado.setId(rs.getLong("id"));
        cuidado.setNome(rs.getString("nome"));
        cuidado.setDescricao(rs.getString("descricao"));
        cuidado.setDiasIntervalo(rs.getInt("dias_intervalo"));
        return cuidado;
    }
}
