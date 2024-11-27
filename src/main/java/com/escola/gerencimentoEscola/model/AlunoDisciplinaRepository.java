package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long> {
    List<AlunoDisciplina> findAllByAlunoId(Long id);
    @Query(
        "select ad from AlunoDisciplina ad where status = coalesce(:status, ad.status) order by coalesce(:order, id)"
    )
    List<AlunoDisciplina> filterAlunoDisciplina(@Param("status") AlunoDisciplinaStatus status, @Param("order") String order);
}
