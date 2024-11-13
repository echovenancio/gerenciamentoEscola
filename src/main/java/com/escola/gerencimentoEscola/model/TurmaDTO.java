package com.escola.gerencimentoEscola.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TurmaDTO {
    Long id;
    String identificador;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<AlunoDTO> alunos = new ArrayList<AlunoDTO>();

    public TurmaDTO(Turma turma) {
        this.id = turma.getId();
        this.identificador = turma.getIdentificador();
        this.alunos = null; 
    }

    public TurmaDTO(Turma turma, boolean withAlunos) {
        this.id = turma.getId();
        this.identificador = turma.getIdentificador();
        if (withAlunos) {
            this.alunos = turma.getAlunos().stream().map(a -> new AlunoDTO(a)).toList();
        } else {
            this.alunos = null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public List<AlunoDTO> getAlunos() {
        return alunos;
    }

}
