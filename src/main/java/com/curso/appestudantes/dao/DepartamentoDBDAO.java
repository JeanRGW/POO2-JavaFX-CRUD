package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Departamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDBDAO implements DepartamentoDAO {
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

    public void insere(Departamento x) throws SQLException {
        open();

        sql = "INSERT INTO Departamento (departamentoId, nome, qntProfessores) VALUES (?, ?, ?);";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x.getDepartamentoId());
        stmt.setString(2, x.getNome());
        stmt.setInt(3, x.getQntProfessores());

        stmt.executeUpdate();

        close();
    }

    public void atualiza(Departamento x) throws SQLException {
        open();

        sql = "UPDATE Departamento SET nome=?, qntProfessores=? WHERE departamentoId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, x.getNome());
        stmt.setInt(2, x.getQntProfessores());
        stmt.setInt(3, x.getDepartamentoId());

        stmt.executeUpdate();
        close();
    }

    public void remove(Departamento x) throws SQLException {
        open();

        sql = "DELETE FROM Departamento WHERE departamentoId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x.getDepartamentoId());

        stmt.executeUpdate();
        close();
    }

    public void removePorId(int x) throws SQLException {
        open();

        sql = "DELETE FROM Departamento WHERE departamentoId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x);

        stmt.executeUpdate();
        close();
    }

    public Departamento buscaPorId(int x) throws SQLException {
        open();

        sql = "SELECT * FROM Departamento WHERE departamentoId=?;";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, x);
        result = stmt.executeQuery();

        if(result.next()){
            Departamento departamento = new Departamento();

            departamento.setDepartamentoId(result.getInt("departamentoId"));
            departamento.setNome(result.getString("nome"));
            departamento.setQntProfessores(result.getInt("qntProfessores"));

            close();
            return departamento;
        } else {
            close();
            return null;
        }
    }

    public List<Departamento> listaTodos() throws SQLException {
        open();

        sql = "SELECT * FROM Departamento;";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();

        ArrayList<Departamento> departamentos = new ArrayList<Departamento>();

        while(result.next()){
            Departamento departamento = new Departamento();

            departamento.setDepartamentoId(result.getInt("departamentoId"));
            departamento.setNome(result.getString("nome"));
            departamento.setQntProfessores(result.getInt("qntProfessores"));

            departamentos.add(departamento);
        }

        close();
        return departamentos;
    }

}
