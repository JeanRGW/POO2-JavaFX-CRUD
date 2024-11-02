package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Estudante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDBDAO implements EstudanteDAO {
    private String sql;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet result;

    private void open() throws SQLException {
        conn = Conexao.getConexao();
    }

    private void close() throws SQLException {
        conn.close();
    }

    public void insere(Estudante e) throws SQLException {
        open();
        sql = "INSERT INTO Estudante (estudanteId, dataNascimento, cpf, nome) VALUES (?, ?, ?, ?);";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, e.getEstudanteId());
        stmt.setDate(2, e.getDataNascimento());
        stmt.setString(3, e.getCpf());
        stmt.setString(4, e.getNome());
        stmt.executeUpdate();
        close();
    }

    public void atualiza(Estudante e) throws SQLException {
        open();
        sql = "UPDATE Estudante SET dataNascimento=?, cpf=?, nome=? WHERE estudanteId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, e.getDataNascimento());
        stmt.setString(2, e.getCpf());
        stmt.setString(3, e.getNome());
        stmt.setInt(4, e.getEstudanteId());
        stmt.executeUpdate();
        close();
    }

    public void remove(Estudante e) throws SQLException {
        open();
        sql = "DELETE FROM Estudante WHERE estudanteId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, e.getEstudanteId());
        stmt.executeUpdate();
        close();
    }

    public void removePorId(int x) throws SQLException {
        open();

        sql = "DELETE FROM Estudante WHERE estudanteId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x);

        stmt.executeUpdate();
        close();
    }

    public Estudante buscaPorId(int estudanteId) throws SQLException {
        open();
        sql = "SELECT * FROM Estudante WHERE estudanteId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, estudanteId);
        result = stmt.executeQuery();

        if (result.next()) {
            Estudante estudante = new Estudante(
                    result.getInt("estudanteId"),
                    result.getDate("dataNascimento"),
                    result.getString("cpf"),
                    result.getString("nome")
            );
            close();
            return estudante;
        } else {
            close();
            return null;
        }
    }

    public List<Estudante> listaTodos() throws SQLException {
        open();
        sql = "SELECT * FROM Estudante;";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        List<Estudante> estudantes = new ArrayList<>();
        while (result.next()) {
            Estudante estudante = new Estudante(
                    result.getInt("estudanteId"),
                    result.getDate("dataNascimento"),
                    result.getString("cpf"),
                    result.getString("nome")
            );
            estudantes.add(estudante);
        }
        close();
        return estudantes;
    }
}
