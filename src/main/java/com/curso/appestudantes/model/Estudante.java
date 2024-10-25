package com.curso.appestudantes.model;

import java.sql.Date;

public class Estudante {
    private int estudanteId;
    private Date dataNascimento;
    private String cpf;
    private String nome;

    public Estudante(int estudanteId, Date dataNasc, String cpf, String nome) {
        this.estudanteId = estudanteId;
        this.dataNascimento = dataNasc;
        this.cpf = cpf;
        this.nome = nome;
    }

    public Estudante(String nome, String cpf, int estudanteId) {
        this.nome = nome;
        this.cpf = cpf;
        this.estudanteId = estudanteId;
    }

    public int getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(int estudanteId) {
        this.estudanteId = estudanteId;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setIdade(Date dataNasc) {
        this.dataNascimento = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Estudante{" +
                "estudanteId=" + estudanteId +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}



