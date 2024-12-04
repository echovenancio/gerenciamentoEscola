package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query(
        "select p from Professor p where p.nome like concat('%', coalesce(:nome , p.nome), '%') and p.email like concat('%', coalesce(:email, p.email), '%') and p.idade = coalesce(:idade, p.idade) order by coalesce(:order, id)"
    )
    List<Professor> filterProfessores(@Param("nome") String nome, @Param("email") String email, @Param("idade") Integer idade, @Param("order") String order);

}
