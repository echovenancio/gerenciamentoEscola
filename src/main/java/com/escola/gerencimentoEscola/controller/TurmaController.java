package com.escola.gerencimentoEscola.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.Turma;
import com.escola.gerencimentoEscola.model.TurmaDTO;
import com.escola.gerencimentoEscola.model.TurmaRepository;

import jakarta.transaction.Transactional;

class PutTurma {
    Optional<String> identificador;
    Optional<List<Long>> addAlunosIds;
    Optional<List<Long>> remAlunosIds;

    public PutTurma(Optional<String> identificador, Optional<List<Long>> addAlunosIds, Optional<List<Long>> remAlunosIds) {
        this.identificador = identificador;
        this.addAlunosIds = addAlunosIds;
        this.remAlunosIds = remAlunosIds;
    }
}




@RestController
class TurmaController {

    @Autowired
    TurmaRepository turmaRepository;
    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping("/turmas")
    public List<TurmaDTO> getTurmas() {
        return turmaRepository.findAll().stream().map(t -> new TurmaDTO(t)).toList();
    }

    @GetMapping("/turmas/{id}")
    public TurmaDTO getTurma(@PathVariable Long id) {
        var maybe_turma = turmaRepository.findById(id);
        if (maybe_turma.isPresent()) {
            return new TurmaDTO(maybe_turma.get(), true);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/turmas")
    @Transactional
    public TurmaDTO postTurma(@RequestBody Turma turma) {
        return new TurmaDTO(turmaRepository.save(turma));
    }

    @PutMapping("/turmas/{id}")
    @Transactional
    public TurmaDTO putTurma(@PathVariable Long id, @RequestBody PutTurma pTurma) {
        Optional<Turma> maybe_turma = turmaRepository.findById(id);
        if (maybe_turma.isPresent()) {
            var turma = maybe_turma.get();
            pTurma.identificador.ifPresent(turma::setIdentificador);
            pTurma.addAlunosIds.ifPresent(ids -> {
                var alunos = alunoRepository.findAllById(ids);
                alunos.forEach(turma::adicionarAluno);
            });
            pTurma.remAlunosIds.ifPresent(ids -> {
                List<Aluno> alunos = alunoRepository.findAllById(ids);
                alunos.forEach(turma::removerAluno);
            });
            turmaRepository.save(turma);
            return new TurmaDTO(turma, true);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/turmas/{id}")
    @Transactional
    public Long deleteTurma(@PathVariable Long id) {
        Optional<Turma> maybe_turma = turmaRepository.findById(id);
        if (maybe_turma.isPresent()) {
            var turma = maybe_turma.get();
            turma.getAlunos().forEach(a -> a.setTurma(null));
            turmaRepository.delete(maybe_turma.get());
            return id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
