package com.escola.gerencimentoEscola.controller;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AlunoController {
    @Autowired
    AlunoService alunoService;

    @GetMapping("/")
    public String Hello() {
        return "Hello World!";
    }
    @GetMapping("/alunos/")
    public List<Aluno> getAlunos() {
        return alunoService.getAlunos();
    }

    @GetMapping("/alunos/{id}")
    public Aluno getAluno(@PathVariable Long id) {
        var maybe_aluno = alunoService.findAlunoById(id);
        if (maybe_aluno.isPresent()) {
            return maybe_aluno.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "aluno não foi encontado");
        }
    }
}
