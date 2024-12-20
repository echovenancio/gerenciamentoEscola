package com.escola.gerencimentoEscola.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.Turma;
import com.escola.gerencimentoEscola.model.TurmaDTO;
import com.escola.gerencimentoEscola.model.TurmaRepository;

import io.swagger.v3.oas.annotations.Operation;
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

class TurmaResource {
    public String identificador;

    public TurmaResource() {}

    public TurmaResource(String identificador) {
        this.identificador = identificador;
    }
}

@RestController
@RequestMapping("/api/turmas")
class TurmaController {

    @Autowired
    TurmaRepository turmaRepository;
    @Autowired
    AlunoRepository alunoRepository;

    @Operation(summary = "Retorna todas as turmas com base na especificação do filtro")
    @GetMapping()
    public List<TurmaDTO> getTurmas(@RequestParam(required = false) String identificador, @RequestParam(required = false) String orderBy) {
        return turmaRepository.filterTurma(identificador, orderBy).stream().map(t -> new TurmaDTO(t)).toList();
    }

    @Operation(summary = "Retorna uma turma com base no id")
    @GetMapping("/{id}")
    public TurmaDTO getTurma(@PathVariable Long id) {
        var maybe_turma = turmaRepository.findById(id);
        if (maybe_turma.isPresent()) {
            return new TurmaDTO(maybe_turma.get(), true);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Cria uma nova turma")
    @PostMapping()
    @Transactional
    public TurmaDTO postTurma(@RequestBody TurmaResource tResource) {
        var identificador = tResource.identificador;
        if (identificador != null) {
            return new TurmaDTO(turmaRepository.save(new Turma(identificador)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita uma turma", description = "Suporte a ações de grupo, podendo remover ou adionar varios alunos simultaneamente")
    @PutMapping("/{id}")
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

    @Operation(summary = "Exclui uma turma")
    @DeleteMapping("/{id}")
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
