package br.com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import br.com.mvc.config.MysqlSingleton;

public class MysqlDAO {
    protected MysqlSingleton banco;

    public MysqlDAO() {
        this.banco = MysqlSingleton.getInstance();
    }

    protected ResultSet executar(String sql, Object... parametros) throws SQLException {
        return this.banco.executar(sql, parametros);
    }

    protected int executarUpdate(String sql, Object... parametros) throws SQLException {
        return this.banco.executarUpdate(sql, parametros);
    }

    protected void alterarExistente(String sql, Object... parametros) throws SQLException {
        if (executarUpdate(sql, parametros) == 0) {
            throw new IllegalArgumentException("O registro não existe mais. Atualize a lista antes de editar.");
        }
    }

    // Consultas constantes dos DAOs, nunca nomes recebidos de uma requisição.
    protected void excluirSemVinculos(Long id, String consultaRegistro, String consultaVinculo,
            String exclusao, String mensagemVinculo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Selecione um registro válido para excluir.");
        }
        // Conexão exclusiva: os bloqueios e a exclusão pertencem à mesma transação.
        try (Connection conexao = banco.abrirConexao()) {
            conexao.setAutoCommit(false);
            try {
                if (!existe(conexao, consultaRegistro, id)) {
                    throw new IllegalArgumentException("O registro não foi encontrado ou já foi excluído.");
                }
                // Bloquear o pai impede novos vínculos até o fim da operação,
                // inclusive em bancos antigos que ainda possuem ON DELETE CASCADE.
                if (consultaVinculo != null && existe(conexao, consultaVinculo, id)) {
                    throw new IllegalArgumentException(mensagemVinculo);
                }
                try (PreparedStatement ps = conexao.prepareStatement(exclusao)) {
                    ps.setLong(1, id);
                    if (ps.executeUpdate() != 1) {
                        throw new IllegalArgumentException("O registro não foi encontrado ou já foi excluído.");
                    }
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    conexao.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir registro.", e);
        }
    }

    private boolean existe(Connection conexao, String sql, Long id) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

