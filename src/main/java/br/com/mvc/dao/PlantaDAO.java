package br.com.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Ambiente;
import br.com.mvc.model.Planta;
import br.com.mvc.model.Usuario;

public class PlantaDAO extends MysqlDAO {

    public PlantaDAO() {
        super();
    }

    public List<Planta> listarTodos() {
        String sql = "SELECT p.id, p.nome_popular, p.nome_cientifico, p.data_aquisicao, p.observacoes, "
                + "p.usuario_id, p.ambiente_id, u.id AS usuario_id_rel, u.nome AS usuario_nome, "
                + "a.id AS ambiente_id_rel, a.nome AS ambiente_nome "
                + "FROM plantas p "
                + "INNER JOIN usuarios u ON u.id = p.usuario_id "
                + "INNER JOIN ambientes a ON a.id = p.ambiente_id "
                + "ORDER BY p.nome_popular";

        List<Planta> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar plantas.", e);
        }
        return lista;
    }

    public Planta buscarPorId(Long id) {
        String sql = "SELECT p.id, p.nome_popular, p.nome_cientifico, p.data_aquisicao, p.observacoes, "
                + "p.usuario_id, p.ambiente_id, u.id AS usuario_id_rel, u.nome AS usuario_nome, "
                + "a.id AS ambiente_id_rel, a.nome AS ambiente_nome "
                + "FROM plantas p "
                + "INNER JOIN usuarios u ON u.id = p.usuario_id "
                + "INNER JOIN ambientes a ON a.id = p.ambiente_id "
                + "WHERE p.id = ?";

        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar planta.", e);
        }
        return null;
    }

    public void inserir(Planta planta) {
        String sql = "INSERT INTO plantas (nome_popular, nome_cientifico, data_aquisicao, observacoes, usuario_id, ambiente_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            super.executarUpdate(sql, planta.getNomePopular(), planta.getNomeCientifico(), planta.getDataAquisicao(),
                    planta.getObservacoes(), planta.getUsuarioId(), planta.getAmbienteId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir planta.", e);
        }
    }

    public void alterar(Planta planta) {
        String sql = "UPDATE plantas SET nome_popular = ?, nome_cientifico = ?, data_aquisicao = ?, observacoes = ?, "
                + "usuario_id = ?, ambiente_id = ? WHERE id = ?";
        try {
            super.alterarExistente(sql, planta.getNomePopular(), planta.getNomeCientifico(), planta.getDataAquisicao(),
                    planta.getObservacoes(), planta.getUsuarioId(), planta.getAmbienteId(), planta.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar planta.", e);
        }
    }

    public void deletar(Long id) {
        excluirSemVinculos(id,
                "SELECT id FROM plantas WHERE id = ? FOR UPDATE",
                "SELECT id FROM lembretes WHERE planta_id = ? LIMIT 1 FOR UPDATE",
                "DELETE FROM plantas WHERE id = ?",
                "Não é possível excluir esta planta: existem lembretes vinculados, inclusive concluídos ou cancelados. Remova ou altere esses lembretes primeiro.");
    }

    private Planta mapear(ResultSet rs) throws SQLException {
        Planta planta = new Planta();
        planta.setId(rs.getLong("id"));
        planta.setNomePopular(rs.getString("nome_popular"));
        planta.setNomeCientifico(rs.getString("nome_cientifico"));
        planta.setDataAquisicao(rs.getString("data_aquisicao"));
        planta.setObservacoes(rs.getString("observacoes"));
        planta.setUsuarioId(rs.getLong("usuario_id"));
        planta.setAmbienteId(rs.getLong("ambiente_id"));

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("usuario_id_rel"));
        usuario.setNome(rs.getString("usuario_nome"));
        planta.setUsuario(usuario);

        Ambiente ambiente = new Ambiente();
        ambiente.setId(rs.getLong("ambiente_id_rel"));
        ambiente.setNome(rs.getString("ambiente_nome"));
        planta.setAmbiente(ambiente);

        return planta;
    }
}
