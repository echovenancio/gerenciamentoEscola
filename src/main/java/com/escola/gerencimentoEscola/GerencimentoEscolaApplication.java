package com.escola.gerencimentoEscola;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.Disciplina;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;
import com.escola.gerencimentoEscola.model.Turma;
import com.escola.gerencimentoEscola.model.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerencimentoEscolaApplication {



    public static void main(String[] args) {
        SpringApplication.run(GerencimentoEscolaApplication.class, args);
    }

//        var joao = new Aluno("aaaaa", "João", 20);
//        var maria = new Aluno("bbbbb", "Maria", 19);
//        alunoRepository.save(joao);
//        alunoRepository.save(maria);
//
//        //var joao = alunoRepository.findById(1L).get();
//        //var maria = alunoRepository.findById(2L).get();
//
//        var dsm = new Turma("dsm");
//        turmaRepository.save(dsm);
//        dsm.adicionarAluno(joao);
//        dsm.adicionarAluno(maria);
//        turmaRepository.save(dsm);
//
//        //var dsm = turmaRepository.findById(1L).get();
//        var joao_matematica = new Disciplina("Matematica", 2.0);
//        var joao_web = new Disciplina("Web", 6.0);
//        joao.adicionarDisciplina(joao_matematica);
//        joao.adicionarDisciplina(joao_web);
//        joao_matematica.atribuirNota(3.5);
//        alunoRepository.save(joao);
//
//        var maria_matematica = new Disciplina("Matematica", 4.0);
//        var maria_web = new Disciplina("Web", 6.5);
//        maria.adicionarDisciplina(maria_matematica);
//        maria.adicionarDisciplina(maria_web);
//        maria.removeDisciplina(maria_matematica);
//        alunoRepository.save(maria);
//
//        System.out.println(
//            String.format(
//                "\nIdentificador da turma de joão: %s",
//                maria.getIdentificadorTurma()
//            )
//        );
//
//        System.out.println(
//            String.format("\nmedia maria: %f", maria.calcularMedia())
//        );
//        System.out.println(
//            String.format("media joão: %f", joao.calcularMedia())
//        );
//        System.out.println(String.format("media dsm: %f", dsm.calcularMedia()));
//
//        var alunos_dsm = alunoRepository.findAlunoByTurma(dsm);
//        System.out.println(
//            String.format("\nalunos em dsm: %d", alunos_dsm.size())
//        );
//        alunos_dsm.forEach(Aluno::exibirInformacoes);
//
//        var aluno_por_nome = alunoRepository.findAlunoByNome("João");
//        System.out.println(
//            String.format("\naluno por nome: %s", aluno_por_nome.toString())
//        );
//        var aluno_por_matricula = alunoRepository.findAlunoByMatricula("bbbbb");
//        System.out.println(
//            String.format(
//                "aluno por matricula: %s",
//                aluno_por_matricula.toString()
//            )
//        );
//
//        System.out.println("\nAlunos ordenados por idade");
//        alunoRepository
//            .alunosOrdenadosPorIdade()
//            .forEach(Aluno::exibirInformacoes);
//
//        System.out.println("\nAlunos ordenados por media");
//        alunoRepository
//            .alunosOrdenadosPorMedia()
//            .forEach(Aluno::exibirInformacoes);
//
//        dsm.removerAluno(maria);
//        turmaRepository.save(dsm);
//        alunoRepository.save(maria);
//
//        System.out.println(
//            String.format(
//                "\nalunos em dsm após remoção de maria: %d",
//                dsm.quantidadeDeAlunos()
//            )
//        );
//        alunoRepository.findAlunoByTurma(dsm).forEach(Aluno::exibirInformacoes);
//        var maybe_maria = dsm.procurarAluno(maria);
//        var maybe_joao = dsm.procurarAluno(joao);
//        if (maybe_maria.isEmpty()) {
//            System.out.println("\nMaria não está presente em dsm");
//        }
//        if (maybe_joao.isPresent()) {
//            System.out.println("João está presente em dsm");
//        }
}
