package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query(
        "select d from Disciplina d where nome like concat('%', coalesce(:nome, d.nome) ,'%') order by coalesce(:order, id)"
    )
    List<Disciplina> filterDisciplina(@Param("nome") String nome, @Param("order") String order);
}
