package br.com.mvc.dao;

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
}
