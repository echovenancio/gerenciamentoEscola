package com.escola.gerencimentoEscola.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String matricula;
    private String nome;
    private int idade;

    public Aluno() {}

    public Aluno(String matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Disciplina> disciplinas = new ArrayList<Disciplina>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Turma turma;

    public double calcularMedia() {
        return this.disciplinas.stream()
            .mapToDouble(Disciplina::getNota)
            .average()
            .orElse(0);
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public void removeDisciplina(Disciplina disciplina) {
        this.disciplinas.remove(disciplina);
    }

    public void exibirInformacoes() {
        System.out.println(this.toString());
    }

    public String getIdentificadorTurma() {
        return this.turma.getIdentificador();
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return String.format(
            "|matricula: %s|nome: %s|idade: %d|media: %f",
            this.matricula,
            this.nome,
            this.idade,
            this.calcularMedia()
        );
    }
}
