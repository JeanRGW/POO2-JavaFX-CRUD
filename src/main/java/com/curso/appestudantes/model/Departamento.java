package com.curso.appestudantes.model;

public class Departamento {
    private int departamentoId;
    private String nome;
    private int qntProfessores;

    public Departamento(){};

    public Departamento(int departamentoId, String nome, int qntProfessores) {
        this.departamentoId = departamentoId;
        this.nome = nome;
        this.qntProfessores = qntProfessores;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQntProfessores() {
        return qntProfessores;
    }

    public void setQntProfessores(int qntProfessores) {
        this.qntProfessores = qntProfessores;
    }

    @Override
    public String toString(){
        return nome;
    }
}
