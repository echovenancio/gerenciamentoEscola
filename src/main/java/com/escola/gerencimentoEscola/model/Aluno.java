package com.escola.gerencimentoEscola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String matricula;
    private String nome;
    private int idade;

    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private List<AlunoDisciplina> disciplinas = new ArrayList<>();

    @Formula("(select COALESCE(avg(d.nota), 0.0) from aluno_disciplina d where d.aluno_id = id)")
    private double media;

    public Aluno() {}

    public Aluno(String matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    @ManyToOne(optional = true)
    @JoinColumn(name = "turma_id", nullable = true)
    private Turma turma;

    public void exibirInformacoes() {
        System.out.println(this.toString());
    }

    public double getMedia() {
        return media;
    }

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<AlunoDisciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<AlunoDisciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void addDisciplina(AlunoDisciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public Turma getTurma() {
        return turma;
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
            this.media
        );
    }
}
