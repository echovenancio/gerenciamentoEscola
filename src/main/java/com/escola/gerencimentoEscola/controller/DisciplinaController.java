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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.escola.gerencimentoEscola.model.Disciplina;
import com.escola.gerencimentoEscola.model.DisciplinaDTO;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;

class DisciplinaResource {
    public String nome;
    DisciplinaResource() {}
    DisciplinaResource(String nome) {
        this.nome = nome;
    }
}

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired 
    DisciplinaRepository disciplinaRepository; 

    @Operation(summary = "Retorna todas as disciplinas de acordo com a especificação do filtro")
    @GetMapping()
    public List<DisciplinaDTO> getDisciplinas(@RequestParam(required = false) String nome, @RequestParam(required = false) String orderBy) {
        return disciplinaRepository.filterDisciplina(nome, orderBy).stream()
            .map(d -> new DisciplinaDTO(d))
            .toList();
    }

    @Operation(summary = "Retorna uma disciplina com base no id")
    @GetMapping("/{id}")
    public DisciplinaDTO getDisciplina(@PathVariable Long id) {
        var maybe_disciplina = disciplinaRepository.findById(id);
        if (maybe_disciplina.isPresent()) {
            return new DisciplinaDTO(maybe_disciplina.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Cria uma nova disciplina")
    @PostMapping()
    @Transactional
    public DisciplinaDTO postDisciplina(@RequestBody DisciplinaResource disciplinaResource) {
        var nome = disciplinaResource.nome;
        if (nome != null) {
            var disciplina = new Disciplina(nome);
            return new DisciplinaDTO(disciplinaRepository.save(disciplina));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita uma disciplina")
    @PutMapping("/{id}")
    @Transactional
    public DisciplinaDTO putDisciplina(@PathVariable Long id, @RequestBody DisciplinaResource disciplinaResource) {
        var nm = disciplinaResource.nome;
        var maybe_disciplina = disciplinaRepository.findById(id);
        if (nm != null && maybe_disciplina.isPresent()) {
            var disciplina = maybe_disciplina.get();
            disciplina.setNome(nm);
            return new DisciplinaDTO(disciplinaRepository.save(disciplina));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Exclui uma disciplina")
    @DeleteMapping("/{id}")
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
