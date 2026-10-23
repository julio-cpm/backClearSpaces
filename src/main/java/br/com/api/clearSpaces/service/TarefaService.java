package com.clearspaces.service;

import com.clearspaces.dto.TarefaDTO;
import com.clearspaces.model.Periodo;
import com.clearspaces.model.Tarefa;
import com.clearspaces.repository.PeriodoRepository;
import com.clearspaces.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Transactional
    public Tarefa criarTarefa(TarefaDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setTipoLimpeza(dto.getTipoLimpeza());
        tarefa.setHorarioConclusao(dto.getHorarioConclusao());

        if (dto.getPeriodoId() != null) {
            Periodo periodo = periodoRepository.findById(dto.getPeriodoId())
                    .orElseThrow(() -> new RuntimeException("Período não encontrado: " + dto.getPeriodoId()));
            tarefa.setPeriodo(periodo);
        }

        if (dto.getProcedimentos() != null) {
            for (String desc : dto.getProcedimentos()) {
                tarefa.adicionarProcedimento(desc);
            }
        }

        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizarTarefa(Long id, TarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));

        tarefa.setDescricao(dto.getDescricao());
        tarefa.setTipoLimpeza(dto.getTipoLimpeza());
        tarefa.setHorarioConclusao(dto.getHorarioConclusao());

        if (dto.getPeriodoId() != null) {
            Periodo periodo = periodoRepository.findById(dto.getPeriodoId())
                    .orElseThrow(() -> new RuntimeException("Período não encontrado: " + dto.getPeriodoId()));
            tarefa.setPeriodo(periodo);
        }

        if (dto.getProcedimentos() != null) {
            tarefa.limparProcedimentos();
            for (String desc : dto.getProcedimentos()) {
                tarefa.adicionarProcedimento(desc);
            }
        }

        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }
}
