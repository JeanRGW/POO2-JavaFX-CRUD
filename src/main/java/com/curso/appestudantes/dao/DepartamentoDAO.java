package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Departamento;

import java.sql.SQLException;
import java.util.List;

public interface DepartamentoDAO {
    public void insere(Departamento x) throws SQLException;
    public void atualiza(Departamento x) throws SQLException;
    public void remove(Departamento x) throws SQLException;

    public Departamento buscaPorId(int departamentoId) throws SQLException;
    public List<Departamento> listaTodos() throws SQLException;
}
