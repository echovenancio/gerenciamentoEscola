package com.escola.gerencimentoEscola.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String identificador;

    @OneToMany(
        mappedBy = "turma",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Aluno> alunos = new ArrayList<Aluno>();

    public Turma() {}

    public Turma(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void adicionarAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.setTurma(this);
    }

    public void removerAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.setTurma(null);
    }

    public Optional<Aluno> procurarAluno(Aluno aluno) {
        int index = this.alunos.indexOf(aluno);
        if (index == -1) {
            return Optional.empty();
        } else {
            return Optional.of(this.alunos.get(index));
        }
    }

    public double calcularMedia() {
        return this.alunos.stream()
            .mapToDouble(Aluno::calcularMedia)
            .average()
            .orElse(0);
    }

    public int quantidadeDeAlunos() {
        return this.alunos.size();
    }
}
