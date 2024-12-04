package com.escola.gerencimentoEscola.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(
        "select a from Aluno a where a.nome like concat('%', coalesce(:nome , a.nome), '%') and a.matricula like concat('%', coalesce(:matricula, a.matricula), '%') and a.idade = coalesce(:idade, a.idade) order by coalesce(:order, id)"
    )
    List<Aluno> filterAlunos(@Param("nome") String nome, @Param("matricula") String matricula, @Param("idade") Integer idade, @Param("order") String order);

}
