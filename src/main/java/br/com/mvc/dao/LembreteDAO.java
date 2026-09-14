package br.com.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Cuidado;
import br.com.mvc.model.Lembrete;
import br.com.mvc.model.Planta;

public class LembreteDAO extends MysqlDAO {

    public LembreteDAO() {
        super();
    }

    public List<Lembrete> listarTodos() {
        String sql = "SELECT l.id, l.planta_id, l.cuidado_id, l.data_agendada, l.data_realizada, l.status, l.observacao, "
                + "p.id AS planta_id_rel, p.nome_popular AS planta_nome, "
                + "c.id AS cuidado_id_rel, c.nome AS cuidado_nome "
                + "FROM lembretes l "
                + "INNER JOIN plantas p ON p.id = l.planta_id "
                + "INNER JOIN cuidados c ON c.id = l.cuidado_id "
                + "ORDER BY l.data_agendada DESC";

        List<Lembrete> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar lembretes.", e);
        }
        return lista;
    }

    public Lembrete buscarPorId(Long id) {
        String sql = "SELECT l.id, l.planta_id, l.cuidado_id, l.data_agendada, l.data_realizada, l.status, l.observacao, "
                + "p.id AS planta_id_rel, p.nome_popular AS planta_nome, "
                + "c.id AS cuidado_id_rel, c.nome AS cuidado_nome "
                + "FROM lembretes l "
                + "INNER JOIN plantas p ON p.id = l.planta_id "
                + "INNER JOIN cuidados c ON c.id = l.cuidado_id "
                + "WHERE l.id = ?";

        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar lembrete.", e);
        }
        return null;
    }

    public void inserir(Lembrete lembrete) {
        String sql = "INSERT INTO lembretes (planta_id, cuidado_id, data_agendada, data_realizada, status, observacao) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            super.executarUpdate(sql, lembrete.getPlantaId(), lembrete.getCuidadoId(), lembrete.getDataAgendada(),
                    lembrete.getDataRealizada(), lembrete.getStatus(), lembrete.getObservacao());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir lembrete.", e);
        }
    }

    public void alterar(Lembrete lembrete) {
        String sql = "UPDATE lembretes SET planta_id = ?, cuidado_id = ?, data_agendada = ?, data_realizada = ?, status = ?, observacao = ? WHERE id = ?";
        try {
            super.executarUpdate(sql, lembrete.getPlantaId(), lembrete.getCuidadoId(), lembrete.getDataAgendada(),
                    lembrete.getDataRealizada(), lembrete.getStatus(), lembrete.getObservacao(), lembrete.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar lembrete.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM lembretes WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir lembrete.", e);
        }
    }

    private Lembrete mapear(ResultSet rs) throws SQLException {
        Lembrete lembrete = new Lembrete();
        lembrete.setId(rs.getLong("id"));
        lembrete.setPlantaId(rs.getLong("planta_id"));
        lembrete.setCuidadoId(rs.getLong("cuidado_id"));
        lembrete.setDataAgendada(rs.getString("data_agendada"));
        lembrete.setDataRealizada(rs.getString("data_realizada"));
        lembrete.setStatus(rs.getString("status"));
        lembrete.setObservacao(rs.getString("observacao"));

        Planta planta = new Planta();
        planta.setId(rs.getLong("planta_id_rel"));
        planta.setNomePopular(rs.getString("planta_nome"));
        lembrete.setPlanta(planta);

        Cuidado cuidado = new Cuidado();
        cuidado.setId(rs.getLong("cuidado_id_rel"));
        cuidado.setNome(rs.getString("cuidado_nome"));
        lembrete.setCuidado(cuidado);

        return lembrete;
    }
}
