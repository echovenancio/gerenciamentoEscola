package com.escola.gerencimentoEscola.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AlunoDTO {
    Long id;
    String matricula;
    String nome;
    String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<AlunoDisciplinaDTO> disciplinas = new ArrayList<AlunoDisciplinaDTO>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    TurmaDTO turma;

    double media;

    int idade;

    public AlunoDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.matricula = aluno.getMatricula();
        this.nome = aluno.getNome();
        this.idade = aluno.getIdade();
        this.disciplinas = null;
        this.turma = null;
        this.media = aluno.getMedia();
        this.email = aluno.getEmail();
    }

    public AlunoDTO(Aluno aluno, boolean withDisciplinas, boolean withTurma) {
        this.id = aluno.getId();
        this.matricula = aluno.getMatricula();
        this.nome = aluno.getNome();
        this.idade = aluno.getIdade();
        this.media = aluno.getMedia();
        this.email = aluno.getEmail();
        if (withDisciplinas && aluno.getDisciplinas() != null) { 
            this.disciplinas = aluno.getDisciplinas().stream()
                .map(d -> new AlunoDisciplinaDTO(d)).toList(); 
        } else {
            this.disciplinas = null;
        }
        if (withTurma && aluno.getTurma() != null) {
            this.turma = new TurmaDTO(aluno.getTurma(), false);
        } else {
            this.turma = null;
        }
    }

    public double getMedia() {
        return media;
    }

    public String getMatricula() {
        return matricula;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public List<AlunoDisciplinaDTO> getDisciplinas() {
        return disciplinas;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public String getEmail() {
        return email;
    }

}
