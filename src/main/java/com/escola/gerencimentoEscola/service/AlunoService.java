package com.escola.gerencimentoEscola.service;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;

    public List<Aluno> getAlunos() {
        System.out.println("aqui");
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public Aluno createAluno(String matricula, String nome, int idade) {
        var aluno = new Aluno(matricula, nome, idade);
        alunoRepository.save(aluno);
        return aluno;
    }

    public Aluno deleteAluno(Long id) {
        var maybe_aluno = alunoRepository.findById(id);
        if (maybe_aluno.isEmpty()) {
            return null;
        }
        var aluno = maybe_aluno.get();
        alunoRepository.delete(aluno);
        return aluno;
    }

    public Aluno updatedAluno(Long id, Optional<String> matricula, Optional<String> nome, Optional<Integer> idade) {
        var maybe_aluno = findAlunoById(id);
        if (maybe_aluno.isEmpty()) {
            return null;
        }
        var aluno = maybe_aluno.get();
        if (matricula.isPresent()) {
            aluno.setMatricula(matricula.get());
        }
        if (nome.isPresent()) {
            aluno.setNome(nome.get());
        }
        if (idade.isPresent()) {
            aluno.setIdade(idade.get());
        }
        alunoRepository.save(aluno);
        return aluno;
    }
}
