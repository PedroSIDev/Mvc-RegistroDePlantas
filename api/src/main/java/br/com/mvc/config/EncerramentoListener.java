package br.com.mvc.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Ao parar o container, encerra a thread de limpeza do driver MySQL e
 * remove o driver do DriverManager (evita os avisos de vazamento do Tomcat).
 */
@WebListener
public class EncerramentoListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent evento) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (Driver driver : Collections.list(DriverManager.getDrivers())) {
            if (driver.getClass().getClassLoader() == loader) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    evento.getServletContext().log("Falha ao remover driver JDBC.", e);
                }
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
