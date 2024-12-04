package com.escola.gerencimentoEscola.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class AlunoDisciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double nota;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @Enumerated(EnumType.ORDINAL)
    private AlunoDisciplinaStatus status;

    @ManyToOne(optional = true)
    @JoinColumn(name="professor_id", nullable = true)
    private Professor professor;

    public AlunoDisciplina() {}

    public AlunoDisciplina(Disciplina disciplina, Aluno aluno, Professor professor, double nota) {
        this.disciplina = disciplina;
        this.aluno = aluno;
        this.professor = professor;
        this.nota = nota;
        this.status = AlunoDisciplinaStatus.EM_CURSO;
    }

    public Long getId() {
        return id;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public AlunoDisciplinaStatus getStatus() {
            return status;
    }

    public void setStatus(AlunoDisciplinaStatus status) {
            this.status = status;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setPrNull() {
        this.professor = null;
    }

    public void finalizar() {
        if (this.nota < 6.0) {
            this.status = AlunoDisciplinaStatus.REPROVADO;
            return;
        }
        this.status = AlunoDisciplinaStatus.CONCLUIDA;
    }
}
