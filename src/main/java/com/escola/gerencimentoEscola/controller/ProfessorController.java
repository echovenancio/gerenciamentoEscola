package com.escola.gerencimentoEscola.controller;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.ProfessorDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplina;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaRepository;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;
import com.escola.gerencimentoEscola.model.Professor;
import com.escola.gerencimentoEscola.model.ProfessorDTO;
import com.escola.gerencimentoEscola.model.ProfessorRepository;
import com.escola.gerencimentoEscola.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;

class PutProfessor {
    public Optional<String> nome;
    public Optional<String> email;
    public Optional<Integer> idade;

    public PutProfessor(Optional<String> nome, Optional<String> email, Optional<Integer> idade) {
        this.email = email;
        this.nome = nome;
        this.idade = idade;
    }
}

class NovoProfessor {
    public String nome;
    public String email;
    public int idade;

    public NovoProfessor(String email, String nome, int idade) {
        this.email = email;
        this.nome = nome;
        this.idade = idade;
    }
}


@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    EmailService emailService;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    AlunoDisciplinaRepository alunoDisciplinaRepositoryu;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @GetMapping()
    public List<ProfessorDTO> getProfessores(@RequestParam(required = false) String nome, @RequestParam(required = false) String email, @RequestParam(required = false) Integer idade, @RequestParam(required = false) String orderBy) {
        return professorRepository.filterProfessores(nome, email, idade, orderBy).stream().map(a -> new ProfessorDTO(a)).toList();
    }

    @GetMapping("/{id}")
    public ProfessorDTO getProfessor(@PathVariable Long id) {
        var maybe_professor = professorRepository.findById(id);
        if (maybe_professor.isPresent()) {
            var professor = maybe_professor.get();
            var professorDTO = new ProfessorDTO(professor, true);
            return professorDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @Transactional
    public Professor postProfessor(@RequestBody NovoProfessor professor) {
        return professorRepository.save(new Professor(professor.nome, professor.email, professor.idade));
    }

    @PutMapping("/{id}")
    @Transactional
    public ProfessorDTO putProfessor(@PathVariable Long id, @RequestBody PutProfessor putProfessor) {
        assert(id != null);
        var maybe_professor = professorRepository.findById(id);
        if (maybe_professor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        var professor = maybe_professor.get();
        putProfessor.nome.ifPresent(professor::setNome);
        putProfessor.idade.ifPresent(professor::setIdade);
        professorRepository.save(professor);
        return new ProfessorDTO(professor);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Long deleteProfessor(@PathVariable Long id) {
        var professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            professorRepository.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
