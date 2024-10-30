package com.escola.gerencimentoEscola.model;

import jakarta.persistence.*;
import org.springframework.data.repository.Repository;

@Entity(name="disciplinas")
public class Disciplina {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private double nota;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    @ManyToOne()
    @JoinColumn(name="id_aluno")
    private Aluno aluno;

    public Disciplina() {}

    public Disciplina(String nome, double nota, Aluno aluno) {
        this.nome = nome;
        this.nota = nota;
        this.aluno = aluno;
    }

    public Disciplina(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public String getNome() {
        return nome;
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
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nota=" + nota +
                ", aluno=" + aluno +
                '}';
    }
}

