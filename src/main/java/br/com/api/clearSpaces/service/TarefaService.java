package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.TarefaDTO;
import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import br.com.api.clearSpaces.repository.TarefaRepository;
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
    public Tarefa salvar(TarefaDTO dto) {
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

    public Tarefa criarTarefa(TarefaDTO dto) {
        return salvar(dto);
    }

    @Transactional
    public Tarefa atualizar(Long id, TarefaDTO dto) {
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

    public Tarefa atualizarTarefa(Long id, TarefaDTO dto) {
        return atualizar(id, dto);
    }

    @Transactional
    public Tarefa salvarFotoAntes(Long id, TarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
        
        String foto = dto.getFotoAntes() != null ? dto.getFotoAntes() : dto.getFoto_antes();
        tarefa.setFotoAntes(foto);
        
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa salvarFotoDepois(Long id, TarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));

        String foto = dto.getFotoDepois() != null ? dto.getFotoDepois() : dto.getFoto_depois();
        tarefa.setFotoDepois(foto);

        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }
}
