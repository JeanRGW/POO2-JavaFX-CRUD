package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.EstudanteDisciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDisciplinaDBDAO implements EstudanteDisciplinaDAO {
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

    public void insere(EstudanteDisciplina ed) throws SQLException {
        open();
        sql = "INSERT INTO EstudanteDisciplina (estudanteId, disciplinaId, estado) VALUES (?, ?, ?);";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, ed.getEstudante().getEstudanteId());
        stmt.setInt(2, ed.getDisciplina().getDisciplinaId());
        stmt.setString(3, ed.getEstado());
        stmt.executeUpdate();
        close();
    }

    public void atualiza(EstudanteDisciplina ed) throws SQLException {
        open();
        sql = "UPDATE EstudanteDisciplina SET estado=? WHERE estudanteId=? AND disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, ed.getEstado());
        stmt.setInt(2, ed.getEstudante().getEstudanteId());
        stmt.setInt(3, ed.getDisciplina().getDisciplinaId());
        stmt.executeUpdate();
        close();
    }

    public void remove(EstudanteDisciplina ed) throws SQLException {
        open();
        sql = "DELETE FROM EstudanteDisciplina WHERE estudanteId=? AND disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, ed.getEstudante().getEstudanteId());
        stmt.setInt(2, ed.getDisciplina().getDisciplinaId());
        stmt.executeUpdate();
        close();
    }

    public EstudanteDisciplina buscaPorIds(int estudanteId, int disciplinaId) throws SQLException {
        open();
        sql = "SELECT * FROM EstudanteDisciplina WHERE estudanteId=? AND disciplinaId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, estudanteId);
        stmt.setInt(2, disciplinaId);
        result = stmt.executeQuery();

        if (result.next()) {
            EstudanteDBDAO estudanteDBDAO = new EstudanteDBDAO();
            DisciplinaDBDAO disciplinaDBDAO = new DisciplinaDBDAO();

            EstudanteDisciplina estudanteDisciplina = new EstudanteDisciplina(
                    estudanteDBDAO.buscaPorId(result.getInt("estudanteId")),
                    disciplinaDBDAO.buscaPorId(result.getInt("disciplinaId")),
                    result.getString("estado")
            );
            close();
            return estudanteDisciplina;
        } else {
            close();
            return null;
        }
    }

    public List<EstudanteDisciplina> listaTodos() throws SQLException {
        open();
        sql = "SELECT * FROM EstudanteDisciplina;";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        List<EstudanteDisciplina> estudanteDisciplinas = new ArrayList<>();
        while (result.next()) {
            EstudanteDBDAO estudanteDBDAO = new EstudanteDBDAO();
            DisciplinaDBDAO disciplinaDBDAO = new DisciplinaDBDAO();

            EstudanteDisciplina estudanteDisciplina = new EstudanteDisciplina(
                    estudanteDBDAO.buscaPorId(result.getInt("estudanteId")),
                    disciplinaDBDAO.buscaPorId(result.getInt("disciplinaId")),
                    result.getString("estado")
            );
            estudanteDisciplinas.add(estudanteDisciplina);
        }
        close();
        return estudanteDisciplinas;
    }

    public List<EstudanteDisciplina> listaPorEstudanteId(int estudanteId) throws SQLException {
        open();
        sql = "SELECT * FROM EstudanteDisciplina WHERE estudanteId=?;";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, estudanteId);

        result = stmt.executeQuery();
        List<EstudanteDisciplina> estudanteDisciplinas = new ArrayList<>();
        while (result.next()) {
            EstudanteDBDAO estudanteDBDAO = new EstudanteDBDAO();
            DisciplinaDBDAO disciplinaDBDAO = new DisciplinaDBDAO();

            EstudanteDisciplina estudanteDisciplina = new EstudanteDisciplina(
                    estudanteDBDAO.buscaPorId(result.getInt("estudanteId")),
                    disciplinaDBDAO.buscaPorId(result.getInt("disciplinaId")),
                    result.getString("estado")
            );
            estudanteDisciplinas.add(estudanteDisciplina);
        }
        close();
        return estudanteDisciplinas;
    }
}
