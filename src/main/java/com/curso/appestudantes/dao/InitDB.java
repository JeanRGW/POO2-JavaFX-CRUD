package com.curso.appestudantes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    public static void init() {
        String sqlDatabase = "CREATE DATABASE appestudantes;";
        String sqlTable = "CREATE TABLE IF NOT EXISTS estudante (" +
                "   estudanteId INT PRIMARY KEY," +
                "   dataNascimento DATE," +
                "   nome VARCHAR(80) NOT NULL," +
                "   cpf INT" +
                ");";

        try {
            Connection conn = Conexao.getConexao();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlDatabase);
            stmt.executeUpdate(sqlTable);

            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
