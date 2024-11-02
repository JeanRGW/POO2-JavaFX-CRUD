package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Disciplina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDBDAO implements DisciplinaDAO {
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

    public void insere(Disciplina d) throws SQLException {
        open();
        sql = "INSERT INTO Disciplina (disciplinaId, nome, departamentoId, cargaHoraria) VALUES (?, ?, ?, ?);";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, d.getDisciplinaId());
        stmt.setString(2, d.getNome());
        stmt.setInt(3, d.getDepartamentoId());
        stmt.setInt(4, d.getCargaHoraria());
        stmt.executeUpdate();
        close();
    }

    public void atualiza(Disciplina d) throws SQLException {
        open();
        sql = "UPDATE Disciplina SET nome=?, departamentoId=?, cargaHoraria=? WHERE disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, d.getNome());
        stmt.setInt(2, d.getDepartamentoId());
        stmt.setInt(3, d.getCargaHoraria());
        stmt.setInt(4, d.getDisciplinaId());
        stmt.executeUpdate();
        close();
    }

    public void remove(Disciplina d) throws SQLException {
        open();
        sql = "DELETE FROM Disciplina WHERE disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, d.getDisciplinaId());
        stmt.executeUpdate();
        close();
    }

    public void removePorId(int x) throws SQLException {
        open();

        sql = "DELETE FROM Disciplina WHERE disciplinaId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x);

        stmt.executeUpdate();
        close();
    }

    public Disciplina buscaPorId(int disciplinaId) throws SQLException {
        open();
        sql = "SELECT * FROM Disciplina WHERE disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, disciplinaId);
        result = stmt.executeQuery();

        if (result.next()) {
            Disciplina disciplina = new Disciplina(
                    result.getInt("disciplinaId"),
                    result.getString("nome"),
                    result.getInt("departamentoId"),
                    result.getInt("cargaHoraria")
            );
            close();
            return disciplina;
        } else {
            close();
            return null;
        }
    }

    public List<Disciplina> listaTodas() throws SQLException {
        open();
        sql = "SELECT * FROM Disciplina;";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        List<Disciplina> disciplinas = new ArrayList<>();
        while (result.next()) {
            Disciplina disciplina = new Disciplina(
                    result.getInt("disciplinaId"),
                    result.getString("nome"),
                    result.getInt("departamentoId"),
                    result.getInt("cargaHoraria")
            );
            disciplinas.add(disciplina);
        }
        close();
        return disciplinas;
    }
}
