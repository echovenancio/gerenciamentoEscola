package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    @Query(
        "select t from Turma t where identificador like concat('%', coalesce(:identificador, t.identificador) ,'%') order by coalesce(:order, id)"
    )
    List<Turma> filterTurma(@Param("identificador") String identificador, @Param("order") String order);
}
