package com.escola.gerencimentoEscola.controller;

import java.util.List;
import java.util.Map;

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

import com.escola.gerencimentoEscola.model.Disciplina;
import com.escola.gerencimentoEscola.model.DisciplinaDTO;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;

import jakarta.transaction.Transactional;

@RestController
public class DisciplinaController {

    @Autowired 
    DisciplinaRepository disciplinaRepository; 

    @GetMapping("/disciplinas")
    public List<DisciplinaDTO> getDisciplinas() {
        return disciplinaRepository.findAll().stream()
            .map(d -> new DisciplinaDTO(d))
            .toList();
    }

    @GetMapping("/disciplinas/{id}")
    public DisciplinaDTO getDisciplina(@PathVariable Long id) {
        var maybe_disciplina = disciplinaRepository.findById(id);
        if (maybe_disciplina.isPresent()) {
            return new DisciplinaDTO(maybe_disciplina.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/disciplinas")
    @Transactional
    public DisciplinaDTO postDisciplina(@RequestBody Map<String, String> post) {
        var nome = post.getOrDefault("nome", null);
        if (nome != null) {
            var disciplina = new Disciplina(nome);
            return new DisciplinaDTO(disciplinaRepository.save(disciplina));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/disciplinas/{id}")
    @Transactional
    public DisciplinaDTO putDisciplina(@PathVariable Long id, @RequestBody Map<String, String> put) {
        var newNome = put.getOrDefault("nome", null);
        var maybe_disciplina = disciplinaRepository.findById(id);
        if (newNome != null && maybe_disciplina.isPresent()) {
            var disciplina = maybe_disciplina.get();
            disciplina.setNome(newNome);
            return new DisciplinaDTO(disciplinaRepository.save(disciplina));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/disciplinas/{id}")
    @Transactional
    public Long deleteDisciplina(@PathVariable Long id) {
        var maybe_disciplina = disciplinaRepository.findById(id);
        if (maybe_disciplina.isPresent()) {
            disciplinaRepository.delete(maybe_disciplina.get());
            return id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
