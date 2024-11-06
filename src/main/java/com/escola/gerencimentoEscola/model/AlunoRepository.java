package com.escola.gerencimentoEscola.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Aluno findAlunoByMatricula(String matricula);

    Aluno findAlunoByNome(String nome);

    List<Aluno> findAlunoByTurma(Turma turma);

    @Query("select a from Aluno a order by a.idade desc")
    List<Aluno> alunosOrdenadosPorIdade();

    @Query(
        "select a from Aluno a join a.disciplinas d group by a.id order by avg(d.nota) desc"
    )
    List<Aluno> alunosOrdenadosPorMedia();
}
