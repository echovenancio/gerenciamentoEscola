package com.escola.gerencimentoEscola.controller;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplina;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaRepository;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;

import jakarta.transaction.Transactional;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;

class PutAluno {
    public Optional<String> matricula;
    public Optional<String> nome;
    public Optional<Integer> idade;

    public PutAluno(Optional<String> matricula, Optional<String> nome, Optional<Integer> idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}


@RestController
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    AlunoDisciplinaRepository alunoDisciplinaRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @GetMapping("/alunos")
    public List<AlunoDTO> getAlunos() {
        return alunoRepository.findAll().stream().map(a -> new AlunoDTO(a)).toList();
    }

    @GetMapping("/alunos/{id}")
    public AlunoDTO getAluno(@PathVariable Long id) {
        var maybe_aluno = alunoRepository.findById(id);
        if (maybe_aluno.isPresent()) {
            var aluno = maybe_aluno.get();
            var alunoDTO = new AlunoDTO(aluno, true, true);
            return alunoDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/alunos")
    @Transactional
    public Aluno postAluno(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @PutMapping("/alunos/{id}")
    @Transactional
    public Aluno putAluno(@PathVariable Long id, @RequestBody PutAluno newAluno) {
        assert(id != null);
        var maybe_aluno = alunoRepository.findById(id);
        if (maybe_aluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        var aluno = maybe_aluno.get();
        newAluno.matricula.ifPresent(aluno::setMatricula);
        newAluno.nome.ifPresent(aluno::setNome);
        newAluno.idade.ifPresent(aluno::setIdade);
        alunoRepository.save(aluno);
        return aluno;
    }

    @DeleteMapping("/alunos/{id}")
    @Transactional
    public Long deleteAluno(@PathVariable Long id) {
        var aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            alunoRepository.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/alunos/{id}/disciplinas")
    public List<AlunoDisciplinaDTO> getDisciplinas(@PathVariable Long id) {
        return alunoDisciplinaRepository.findAllByAlunoId(id)
            .stream()
            .map(ad -> new AlunoDisciplinaDTO(ad))
            .toList();
    }

    @GetMapping("/alunos/{}/disciplinas/{id}")
    public AlunoDisciplinaDTO getDisciplina(@PathVariable Long id) {
        var maybe_disciplina = alunoDisciplinaRepository.findById(id);
        if (maybe_disciplina.isPresent()) {
            return new AlunoDisciplinaDTO(maybe_disciplina.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/alunos/{alunoId}/disciplinas")
    @Transactional
    public AlunoDisciplinaDTO postDisciplina(
        @PathVariable Long alunoId, 
        @RequestBody Map<String, Long> post) {
        System.out.println(post.values());
        Long disciplinaId = post.getOrDefault("disciplina_id", null);
        System.out.println(disciplinaId);
        if (disciplinaId != null) {
            var maybe_disciplina = disciplinaRepository.findById(disciplinaId);
            var maybe_aluno = alunoRepository.findById(alunoId);
            if (maybe_disciplina.isPresent() && maybe_aluno.isPresent()) {
                var alunoDisciplina = new AlunoDisciplina(maybe_disciplina.get(), maybe_aluno.get(), 0.0);
                return new AlunoDisciplinaDTO(alunoDisciplinaRepository.save(alunoDisciplina));
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/alunos/{alunoId}/disciplinas/{disciplinaId}")
    @Transactional
    public AlunoDisciplinaDTO putDisciplina(
        @PathVariable Long disciplinaId, 
        @RequestBody Map<String, Double> put) {
        System.out.println(put.keySet());
        System.out.println(put.values());
        Double nota = put.getOrDefault("nota", null);
        System.out.println(nota);
        if (nota != null) {
            var maybe_alunoDisciplina = alunoDisciplinaRepository.findById(disciplinaId);
            System.out.println(maybe_alunoDisciplina.get().getId());
            if (maybe_alunoDisciplina.isPresent()) {
                var alunoDisciplina = maybe_alunoDisciplina.get();
                alunoDisciplina.setNota(nota);
                return new AlunoDisciplinaDTO(alunoDisciplinaRepository.save(alunoDisciplina));
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/alunos/{alunoId}/disciplinas/{disciplinaId}")
    @Transactional
    public Long deleteDisciplina(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        var maybe_alunoDisciplina = alunoDisciplinaRepository.findById(disciplinaId);
        if (maybe_alunoDisciplina.isPresent()) {
            var alunoDisciplina = maybe_alunoDisciplina.get();
            alunoDisciplina.setDisciplina(null);
            alunoDisciplina.setAluno(null);
            alunoDisciplinaRepository.delete(alunoDisciplina);
            return disciplinaId;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
