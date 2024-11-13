package com.escola.gerencimentoEscola.model;

public class AlunoDisciplinaDTO {
    Long id;
    String nome;
    double nota;

    public AlunoDisciplinaDTO(AlunoDisciplina alunoDisciplina) {
        this.id = alunoDisciplina.getId();
        this.nome = alunoDisciplina.getDisciplina().getNome();
        this.nota = alunoDisciplina.getNota();
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
}
