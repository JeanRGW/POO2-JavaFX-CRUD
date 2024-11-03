package com.curso.appestudantes.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class InitDB {
    private static final List<String> criacaoTabelas = Arrays.asList(
            "CREATE TABLE IF NOT EXISTS estudante (" +
                    "   estudanteId INT PRIMARY KEY," +
                    "   dataNascimento DATE," +
                    "   nome VARCHAR(80) NOT NULL," +
                    "   cpf VARCHAR(11)" +
                    ");",

            "CREATE TABLE IF NOT EXISTS departamento (" +
                    "   departamentoId INT PRIMARY KEY," +
                    "   nome VARCHAR(80) NOT NULL," +
                    "   qntProfessores INT" +
                    ");",

            "CREATE TABLE IF NOT EXISTS disciplina (" +
                    "   disciplinaId INT PRIMARY KEY," +
                    "   nome VARCHAR(80) NOT NULL," +
                    "   cargaHoraria INT," +
                    "   departamentoId INT REFERENCES departamento(departamentoId) ON DELETE CASCADE" +
                    ");",

            "CREATE TABLE IF NOT EXISTS estudanteDisciplina (" +
                    "estudanteId INT REFERENCES estudante(estudanteId) ON DELETE CASCADE," +
                    "disciplinaId INT REFERENCES disciplina(disciplinaId) ON DELETE CASCADE," +
                    "estado VARCHAR(30)," +
                    "PRIMARY KEY (estudanteId, disciplinaId)" +
                    ");"
    );

    public static void init() throws SQLException {
        Connection conn = Conexao.getConexao();
        Statement stmt = conn.createStatement();

        for(String tabela: criacaoTabelas){
            try {
                stmt.executeUpdate(tabela);
            } catch (SQLException e) {
                System.out.println("Erro na execução de: " + tabela);
                e.printStackTrace();
            }
        }

        System.out.println("Banco de dados inicializado.");
    }
}
