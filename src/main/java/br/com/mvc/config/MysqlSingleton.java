package br.com.mvc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlSingleton {

    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String URL = "jdbc:mysql://" + HOST + ":3306/mvcplantas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = System.getenv().getOrDefault("DB_USER", "mvc_user");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "mvc123");

    private static MysqlSingleton instance;
    private Connection conexao;

    private MysqlSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver JDBC: " + e.getMessage());
        }
    }

    public static synchronized MysqlSingleton getInstance() {
        if (instance == null) {
            instance = new MysqlSingleton();
        }
        return instance;
    }

    public Connection obterConexao() throws SQLException {
        if (this.conexao != null && !this.conexao.isClosed()) {
            return this.conexao;
        }

        SQLException lastException = null;
        for (int tentativa = 1; tentativa <= 15; tentativa++) {
            try {
                this.conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                return this.conexao;
            } catch (SQLException e) {
                lastException = e;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }

        throw lastException != null ? lastException : new SQLException("Não foi possível estabelecer conexão com o banco de dados.");
    }

    public ResultSet executar(String sql, Object... parametros) throws SQLException {
        Connection conn = this.obterConexao();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i + 1, parametros[i]);
        }
        return ps.executeQuery();
    }

    public int executarUpdate(String sql, Object... parametros) throws SQLException {
        Connection conn = this.obterConexao();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            return ps.executeUpdate();
        }
    }
}
