package com.escola.gerencimentoEscola.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity(name = "turmas")
public class Turma {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    @OneToMany(targetEntity = Aluno.class, fetch = FetchType.EAGER)
    private Set<Aluno> alunos;

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void adicionarAluno(Aluno aluno) {
        this.alunos.add(aluno);
    }
}
