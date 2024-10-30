package com.escola.gerencimentoEscola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.repository.Repository;

@Entity
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricula;
    private int idade;
    private String turma;

    public Aluno(){}

    public Aluno(String matricula, int idade, String turma) {
        this.matricula = matricula;
        this.idade = idade;
        this.turma = turma;
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
        return turma;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public double calcularMedia() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", idade=" + idade +
                ", turma='" + turma + '\'' +
                '}';
    }
}

interface AlunoRepository extends Repository<Aluno, Long> {

}