package com.curso.appestudantes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartamentoTest {

    @Test
    void getNome() {
        Departamento departamento = new Departamento(1, "Computação", 12);
        String resultado = departamento.getNome();
        assertEquals("Computação", resultado);
    }

    @Test
    void setNome() {
        Departamento departamento = new Departamento(1, "Computaca", 12);
        departamento.setNome("Computação");
        String resultado = departamento.getNome();
        assertEquals("Computação", resultado);
    }

    @Test
    void getQntProfessores() {
        Departamento departamento = new Departamento(1, "Computação", 12);
        int resultado = departamento.getQntProfessores();
        assertEquals(12, resultado);
    }

    @Test
    void setQntProfessores() {
        Departamento departamento = new Departamento(1, "Computação", 12);
        departamento.setQntProfessores(18);
        int resultado = departamento.getQntProfessores();
        assertEquals(18, resultado);
    }
}