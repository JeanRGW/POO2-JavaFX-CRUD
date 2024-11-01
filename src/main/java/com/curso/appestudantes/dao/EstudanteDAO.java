package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Estudante;

import java.sql.SQLException;
import java.util.List;

public interface EstudanteDAO {
    public void insere(Estudante e) throws SQLException;
    public void atualiza(Estudante e) throws SQLException;
    public void remove(Estudante e) throws SQLException;

    public Estudante buscaPorId(int estudanteId) throws SQLException;
    public List<Estudante> listaTodos() throws SQLException;
}
