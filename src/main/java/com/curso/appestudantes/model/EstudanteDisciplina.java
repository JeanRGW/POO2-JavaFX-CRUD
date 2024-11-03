package com.curso.appestudantes.model;

public class EstudanteDisciplina {
    private Estudante estudante;
    private Disciplina disciplina;
    private String estado;

    public EstudanteDisciplina(Estudante estudante, Disciplina disciplina, String estado) {
        this.estudante = estudante;
        this.disciplina = disciplina;
        this.estado = estado;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
