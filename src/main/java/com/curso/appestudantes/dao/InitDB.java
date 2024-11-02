package com.curso.appestudantes.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    public static void init() {
        String sqlTable = "CREATE TABLE estudante (" +
                "   estudanteId INT PRIMARY KEY," +
                "   dataNascimento DATE," +
                "   nome VARCHAR(80) NOT NULL," +
                "   cpf VARCHAR(11)" +
                ");" +
                "CREATE TABLE departamento (" +
                "   departamentoId INT PRIMARY KEY," +
                "   nome VARCHAR(80) NOT NULL," +
                "   qntProfessores INT" +
                ");" +
                "CREATE TABLE disciplina (" +
                "   disciplinaId INT PRIMARY KEY," +
                "   nome VARCHAR(80) NOT NULL," +
                "   cargaHoraria INT," +
                "   departamentoId INT REFERENCES departamento (departamentoId)" +
                ");";

        try {
            Connection conn = Conexao.getConexao();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlTable);

            System.out.println("Banco de dados inicializado.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
