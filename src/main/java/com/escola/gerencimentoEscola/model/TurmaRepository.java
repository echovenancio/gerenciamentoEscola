package com.escola.gerencimentoEscola.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TurmaRepository extends CrudRepository<Turma, Long> {
    @Query("select * from alunos inner join turma_alunos on alunos.id == turma_alunos.id where turma_alunos == ?1")
    Set<Aluno> buscarAlunos(Long turmaId);
}
