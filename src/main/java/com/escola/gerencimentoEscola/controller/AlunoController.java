package com.escola.gerencimentoEscola.controller;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplina;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaDTO;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaRepository;
import com.escola.gerencimentoEscola.model.AlunoDisciplinaStatus;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;
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


class AddDisciplina {
    public Long disciplinaId;
    public Long professorId;

    public AddDisciplina(Long disciplinaId, Long professorId) {
        this.disciplinaId = disciplinaId;
        this.professorId = professorId;
    }
}

class PutAluno {
    public Optional<String> matricula;
    public Optional<String> nome;
    public Optional<Integer> idade;
    public Optional<String> email;

    public PutAluno(Optional<String> matricula, Optional<String> nome, Optional<Integer> idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}

class NovoAluno {
    public String matricula;
    public String nome;
    public int idade;
    public String email;

    public NovoAluno(String matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}

class PutDisciplinaAluno {
    public double nota;
    public boolean finalizar;

    public PutDisciplinaAluno(double nota, boolean finalizar) {
        this.nota = nota;
        this.finalizar = finalizar;
    }
}


@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    EmailService emailService;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    AlunoDisciplinaRepository alunoDisciplinaRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Operation(summary="Retorna os alunos de acordo com a especificação do filtro")
    @GetMapping()
    public List<AlunoDTO> getAlunos(@RequestParam(required = false) String nome, @RequestParam(required = false) String matricula, @RequestParam(required = false) Integer idade, @RequestParam(required = false) String orderBy) {
        return alunoRepository.filterAlunos(nome, matricula, idade, orderBy).stream().map(a -> new AlunoDTO(a)).toList();
    }

    @Operation(summary = "Buscar um aluno com base no id")
    @GetMapping("/{id}")
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

    @Operation(summary = "Cria um novo aluno")
    @PostMapping()
    @Transactional
    public Aluno postAluno(@RequestBody NovoAluno aluno) {
        return alunoRepository.save(new Aluno(aluno.matricula, aluno.nome, aluno.email, aluno.idade));
    }

    @Operation(summary = "Edita um aluno já existente no banco")
    @PutMapping("/{id}")
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
        newAluno.email.ifPresent(aluno::setEmail);
        alunoRepository.save(aluno);
        return aluno;
    }

    @Operation(summary = "Exclui um aluno")
    @DeleteMapping("/{id}")
    @Transactional
    public Long deleteAluno(@PathVariable Long id) {
        var aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            alunoRepository.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Retorna todas as disciplinas de um aluno")
    @GetMapping("/{id}/disciplinas")
    public List<AlunoDisciplinaDTO> getDisciplinas(@PathVariable Long id) {
        return alunoDisciplinaRepository.findAllByAlunoId(id)
            .stream()
            .map(ad -> new AlunoDisciplinaDTO(ad))
            .toList();
    }

    @Operation(summary = "Retorna uma disciplina de um aluno")
    @GetMapping("/{alunoId}/disciplinas/{id}")
    public AlunoDisciplinaDTO getDisciplina(@PathVariable Long id) {
        var maybe_disciplina = alunoDisciplinaRepository.findById(id);
        if (maybe_disciplina.isPresent()) {
            return new AlunoDisciplinaDTO(maybe_disciplina.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Registra o aluno em uma disciplina")
    @PostMapping("/{alunoId}/disciplinas")
    @Transactional
    public AlunoDisciplinaDTO postDisciplina(
        @PathVariable Long alunoId, 
        @RequestBody AddDisciplina addDisciplina) {
        Long disciplinaId = addDisciplina.disciplinaId;
        Long professorId = addDisciplina.professorId;
        System.out.println(disciplinaId);
        if (disciplinaId != null) {
            var maybe_disciplina = disciplinaRepository.findById(disciplinaId);
            var maybe_aluno = alunoRepository.findById(alunoId);
            var maybe_professor = professorRepository.findById(professorId);
            if (maybe_disciplina.isPresent() && maybe_aluno.isPresent() && maybe_professor.isPresent()) {
                var alunoDisciplina = new AlunoDisciplina(
                    maybe_disciplina.get(), 
                    maybe_aluno.get(),
                    maybe_professor.get(), 0.0);
                return new AlunoDisciplinaDTO(alunoDisciplinaRepository.save(alunoDisciplina));
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }



    @Operation(summary = "Edita uma disciplina")
    @PutMapping("/{alunoId}/disciplinas/{disciplinaId}")
    @Transactional
    public AlunoDisciplinaDTO putDisciplina(
        @PathVariable Long disciplinaId, 
        @RequestBody PutDisciplinaAluno putDisciplinaAluno) {
        Double nota = putDisciplinaAluno.nota;
        System.out.println(nota);
        var maybe_alunoDisciplina = alunoDisciplinaRepository.findById(disciplinaId);
        System.out.println(maybe_alunoDisciplina.get().getId());
        if (maybe_alunoDisciplina.isPresent()) {
            var alunoDisciplina = maybe_alunoDisciplina.get();
            if (nota != null) {
                alunoDisciplina.setNota(nota);
            }
            if (putDisciplinaAluno.finalizar) {
                alunoDisciplina.finalizar();
            }
            return new AlunoDisciplinaDTO(alunoDisciplina);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Exclui uma disciplina do aluno")
    @DeleteMapping("/{alunoId}/disciplinas/{disciplinaId}")
    @Transactional
    public Long deleteDisciplina(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        var maybe_alunoDisciplina = alunoDisciplinaRepository.findById(disciplinaId);
        if (maybe_alunoDisciplina.isPresent()) {
            var alunoDisciplina = maybe_alunoDisciplina.get();
            alunoDisciplina.setDisciplina(null);
            alunoDisciplina.setAluno(null);
            alunoDisciplina.setProfessor(null);
            alunoDisciplinaRepository.delete(alunoDisciplina);
            return disciplinaId;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
