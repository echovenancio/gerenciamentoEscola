package com.escola.gerencimentoEscola.model;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private int idade;
    private String email;

    @OneToMany
    private List<AlunoDisciplina> disciplinas;

    public Professor() {}

    public Professor(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    } 

    public Long getId() {
        return Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AlunoDisciplina> getDisciplinas() {
        return this.disciplinas;
    }

}
