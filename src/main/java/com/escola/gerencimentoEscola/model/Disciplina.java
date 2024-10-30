package com.escola.gerencimentoEscola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.repository.Repository;

@Entity
public class Disciplina {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private double nota;
}

interface DisciplinaRepository extends Repository<Disciplina, Long> {

}
