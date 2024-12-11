package com.curso.appestudantes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisciplinaTest {

    @Test
    void getNome() {
        Disciplina disciplina = new Disciplina(1, "Banco de Dados 1", 1, 2);
        String resultado = disciplina.getNome();
        assertEquals("Banco de Dados 1", resultado);
    }

    @Test
    void setNome() {
        Disciplina disciplina = new Disciplina(1, "Banco de Dados 1", 1, 2);
        disciplina.setNome("Banco de Dados 2");
        String resultado = disciplina.getNome();
        assertEquals("Banco de Dados 2", resultado);
    }

    @Test
    void getCargaHoraria() {
        Disciplina disciplina = new Disciplina(1, "Banco de Dados 1", 1, 2);
        int resultado = disciplina.getCargaHoraria();
        assertEquals(2, resultado);
    }

    @Test
    void setCargaHoraria() {
        Disciplina disciplina = new Disciplina(1, "Banco de Dados 1", 1, 2);
        disciplina.setCargaHoraria(60);
        int resultado = disciplina.getCargaHoraria();
        assertEquals(60, resultado);
    }
}