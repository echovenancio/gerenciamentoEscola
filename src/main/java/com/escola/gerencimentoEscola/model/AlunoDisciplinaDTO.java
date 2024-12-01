package com.escola.gerencimentoEscola.model;

public class AlunoDisciplinaDTO {
    Long id;
    String nome;
    double nota;
    AlunoDisciplinaStatus status;
    String professor;

    public AlunoDisciplinaDTO(AlunoDisciplina alunoDisciplina) {
        this.id = alunoDisciplina.getId();
        this.nome = alunoDisciplina.getDisciplina().getNome();
        this.nota = alunoDisciplina.getNota();
        this.status = alunoDisciplina.getStatus();
        this.professor = alunoDisciplina.getProfessor().getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getStatus() {
        switch (this.status) {
            case CONCLUIDA:
                return "concluido";
            case EM_CURSO:
                return "em curso";
            default:
                return "";
        }
    }
}
