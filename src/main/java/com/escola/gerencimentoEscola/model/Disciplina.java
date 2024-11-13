package com.escola.gerencimentoEscola.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY)
    private List<AlunoDisciplina> disciplinas = new ArrayList<>();

    public Disciplina() {}

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public List<AlunoDisciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<AlunoDisciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void exibirInformacoes() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return String.format("+nome: %s\n|nota: %f\n", this.nome);
    }
}
