package com.curso.appestudantes.model;

public class EstudanteDisciplina {
    private int estudanteId;
    private int disciplinaId;
    private String estado;

    public EstudanteDisciplina(int estudanteId, int disciplinaId, String estado) {
        this.estudanteId = estudanteId;
        this.disciplinaId = disciplinaId;
        this.estado = estado;
    }

    public int getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(int estudanteId) {
        this.estudanteId = estudanteId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
