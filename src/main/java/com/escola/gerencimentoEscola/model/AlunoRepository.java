package com.escola.gerencimentoEscola.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
}
