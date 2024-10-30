package com.escola.gerencimentoEscola.model;

import jakarta.persistence.*;
import org.hibernate.sql.results.graph.entity.internal.DiscriminatedEntityInitializer;
import org.springframework.data.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name="alunos")
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricula;
    private String nome;
    private int idade;
    private String nomeTurma;

    @ManyToOne
    private Turma turma;

    public Aluno(){}

    public Aluno(String matricula, String nome, int idade, String turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.idade = idade;
        this.nomeTurma = turma;
    }

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getIdade() {
        return idade;
    }

    public String getTurma() {
        return nomeTurma;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setTurma(String turma) {
        this.nomeTurma = turma;
    }

    public double calcularMedia(DisciplinaRepository repository) {
        var disciplinas = repository.findByAlunoId(this.id);
        double media = 0.0;
        int cnt = 0;
        for (Disciplina d: disciplinas) {
            media += d.getNota();
            cnt++;
        }
        return media / cnt;
    }

    public void adicionaDisciplina(DisciplinaRepository repository, Disciplina d) {
        d.setAluno(this);
        repository.save(d);
    }

    public void deleteDisciplina(DisciplinaRepository repository, Disciplina d) {
        repository.deleteById(d.getId());
    }

    public void exibirInformacoes() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", turma='" + nomeTurma + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
