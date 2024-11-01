package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.EstudanteDisciplina;

import java.sql.SQLException;
import java.util.List;

public interface EstudanteDisciplinaDAO {
    public void insere(EstudanteDisciplina ed) throws SQLException;
    public void atualiza(EstudanteDisciplina ed) throws SQLException;
    public void remove(EstudanteDisciplina ed) throws SQLException;

    public EstudanteDisciplina buscaPorIds(int estudanteId, int disciplinaId) throws SQLException;
    public List<EstudanteDisciplina> listaTodos() throws SQLException;
}
