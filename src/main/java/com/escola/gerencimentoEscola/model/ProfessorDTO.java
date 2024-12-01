package com.escola.gerencimentoEscola.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProfessorDTO {
    Long id;
    String email;
    String nome;
    int idade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<AlunoDisciplinaDTO> disciplinas = new ArrayList<AlunoDisciplinaDTO>();

    public ProfessorDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.idade = professor.getIdade();
        this.disciplinas = null;
    }

    public ProfessorDTO(Professor professor, boolean withDisciplinas) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.idade = professor.getIdade();
        if (withDisciplinas && professor.getDisciplinas() != null) { 
            this.disciplinas = professor.getDisciplinas().stream()
                .map(d -> new AlunoDisciplinaDTO(d)).toList(); 
        } else {
            this.disciplinas = null;
        }
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

}
