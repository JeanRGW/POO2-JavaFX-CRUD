package com.curso.appestudantes.model;

public class Disciplina {
    private int disciplinaId;
    private String nome;
    private int departamentoId;
    private int cargaHoraria;

    public Disciplina(){}

    public Disciplina(int disciplinaId, String nome, int departamentoId, int cargaHoraria) {
        this.disciplinaId = disciplinaId;
        this.nome = nome;
        this.departamentoId = departamentoId;
        this.cargaHoraria = cargaHoraria;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "disciplinaId=" + disciplinaId +
                ", nome='" + nome + '\'' +
                ", departamentoId=" + departamentoId +
                ", cargaHoraria=" + cargaHoraria +
                '}';
    }
}
