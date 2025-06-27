// ConnectionFactory.java
package br.ifsc.tubarao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/ifsc_db"; // Altere se necessário
    private static final String USER = "postgres"; // Altere se necessário
    private static final String PASSWORD = "admin"; // Altere se necessário

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao obter conexão com o banco de dados.", e);
        }
    }
}

