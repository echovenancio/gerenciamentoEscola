package com.escola.gerencimentoEscola.model;

public class AlunoDisciplinaDTO {
    Long id;
    String nome;
    double nota;
    AlunoDisciplinaStatus status;

    public AlunoDisciplinaDTO(AlunoDisciplina alunoDisciplina) {
        this.id = alunoDisciplina.getId();
        this.nome = alunoDisciplina.getDisciplina().getNome();
        this.nota = alunoDisciplina.getNota();
        this.status = alunoDisciplina.getStatus();
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
