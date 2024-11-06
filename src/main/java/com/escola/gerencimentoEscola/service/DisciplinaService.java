package com.escola.gerencimentoEscola.service;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoRepository;
import com.escola.gerencimentoEscola.model.Disciplina;
import com.escola.gerencimentoEscola.model.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {
    @Autowired
    DisciplinaRepository disciplinaRepository;
    @Autowired
    AlunoRepository alunoRepository;

    @Transactional
    public void atribuirNota(Disciplina disciplina, double nota) {
        disciplina.setNota(nota);
        disciplinaRepository.save(disciplina);
    }
}
