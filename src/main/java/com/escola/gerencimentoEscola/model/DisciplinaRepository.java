package com.escola.gerencimentoEscola.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
    List<Disciplina> findByAlunoId(Long aluno);
}

