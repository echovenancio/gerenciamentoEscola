package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long> {
    List<AlunoDisciplina> findAllByAlunoId(Long id);
}
