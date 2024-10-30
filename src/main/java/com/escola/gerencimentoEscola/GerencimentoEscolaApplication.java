package com.escola.gerencimentoEscola;

import com.escola.gerencimentoEscola.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerencimentoEscolaApplication implements CommandLineRunner {

	@Autowired
	AlunoRepository alunoRepository;
	@Autowired
	DisciplinaRepository disciplinaRepository;
	@Autowired
	TurmaRepository turmaRepository;

	public static void main(String[] args) {
		SpringApplication.run(GerencimentoEscolaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var aluno = alunoRepository.findById(102L).get();
		var aluno2 = alunoRepository.findById(152L).get();
		var turma = turmaRepository.findById(1L).get();
		//var turma = new Turma();
		//turmaRepository.save(turma);
		//turma.adicionarAluno(aluno);
		//turma.adicionarAluno(aluno2);
		//alunoRepository.save(aluno);
		//alunoRepository.save(aluno2);
		//turmaRepository.save(turma);
		turmaRepository.buscarAlunos(turma.getId()).forEach(System.out::println);
		//var web = new Disciplina("web", 7.0);
		//var portugues = new Disciplina("portugues", 1.0);
		//var matematica = new Disciplina("matematica", 0.0);
		//aluno.adicionaDisciplina(disciplinaRepository, web);
		//aluno.adicionaDisciplina(disciplinaRepository, portugues);
		//aluno.adicionaDisciplina(disciplinaRepository, matematica);
		//var aluno2 = new Aluno("adsad", "galvao", 82, "todas");
		//alunoRepository.save(aluno2);
		//var arduino = new Disciplina("arduino", 10.0);
		//aluno2.adicionaDisciplina(disciplinaRepository, arduino);
		//System.out.println(aluno.calcularMedia(disciplinaRepository));
		//System.out.println(aluno2.calcularMedia(disciplinaRepository));
	}

}
