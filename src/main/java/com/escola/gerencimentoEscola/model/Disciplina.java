package com.escola.gerencimentoEscola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private double nota;

    public Disciplina() {}

    public Disciplina(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    public void atribuirNota(double nota) {
        this.nota = nota;
    }

    public void exibirInformacoes() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return String.format("+nome: %s\n|nota: %f\n", this.nome, this.nota);
    }
}
