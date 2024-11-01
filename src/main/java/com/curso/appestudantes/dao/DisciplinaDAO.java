package com.curso.appestudantes.dao;

import com.curso.appestudantes.model.Disciplina;

import java.sql.SQLException;
import java.util.List;

public interface DisciplinaDAO {
    public void insere(Disciplina d) throws SQLException;
    public void atualiza(Disciplina d) throws SQLException;
    public void remove(Disciplina d) throws SQLException;

    public Disciplina buscaPorId(int disciplinaId) throws SQLException;
    public List<Disciplina> listaTodas() throws SQLException;
}
