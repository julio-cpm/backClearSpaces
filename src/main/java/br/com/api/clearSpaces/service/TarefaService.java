package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.ProcedimentoDTO;
import br.com.api.clearSpaces.dto.TarefaDTO;
import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.entity.Procedimento;
import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import br.com.api.clearSpaces.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final PeriodoRepository periodoRepository;

    public TarefaService(TarefaRepository tarefaRepository, PeriodoRepository periodoRepository) {
        this.tarefaRepository = tarefaRepository;
        this.periodoRepository = periodoRepository;
    }

    @Transactional
    public Tarefa salvar(TarefaDTO dto) {
        Periodo periodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        Tarefa novaTarefa = new Tarefa(dto.getDescricao(), dto.getTipoLimpeza(), periodo, dto.getHorarioConclusao());

        if (dto.getProcedimentos() != null) {
            for (ProcedimentoDTO procDTO : dto.getProcedimentos()) {
                Procedimento proc = new Procedimento(procDTO.getDescricao(), novaTarefa);
                proc.setConcluido(procDTO.isConcluido());
                novaTarefa.adicionarProcedimento(proc);
            }
        }

        return tarefaRepository.save(novaTarefa);
    }

    @Transactional
    public Tarefa salvarFotoAntes(Long id, TarefaDTO dto) {
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        
        tarefaEncontrada.setFoto_antes(dto.getFoto_antes());
        return tarefaRepository.save(tarefaEncontrada);
    }

    @Transactional
    public Tarefa salvarFotoDepois(Long id, TarefaDTO dto) {
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        
        tarefaEncontrada.setFoto_depois(dto.getFoto_depois());
        return tarefaRepository.save(tarefaEncontrada);
    }

    /**
     * Atualiza os status dos procedimentos recebidos e tenta finalizar a tarefa.
     */
    @Transactional
    public Tarefa concluirTarefa(Long id, TarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));

        // Atualiza a foto do 'depois' se enviada
        if (dto.getFoto_depois() != null) {
            tarefa.setFoto_depois(dto.getFoto_depois());
        }

        // Atualiza o estado dos procedimentos enviados pelo frontend (checkboxes marcados)
        if (dto.getProcedimentos() != null && !dto.getProcedimentos().isEmpty()) {
            for (ProcedimentoDTO procDTO : dto.getProcedimentos()) {
                tarefa.getProcedimentos().stream()
                        .filter(p -> p.getId().equals(procDTO.getId()))
                        .findFirst()
                        .ifPresent(p -> p.setConcluido(procDTO.isConcluido()));
            }
        }

        // Valida se todos os procedimentos foram concluídos
        if (!tarefa.todosProcedimentosConcluidos()) {
            throw new IllegalStateException("Não é possível concluir a tarefa: existem procedimentos pendentes.");
        }

        tarefa.setHorarioConclusao(LocalDateTime.now());
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    @Transactional
    public Tarefa atualizar(Long id, TarefaDTO dto) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));

        Periodo novoPeriodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        tarefaExistente.setDescricao(dto.getDescricao());
        tarefaExistente.setTipoLimpezaEnum(dto.getTipoLimpeza());
        tarefaExistente.setPeriodo(novoPeriodo);
        tarefaExistente.setHorarioConclusao(dto.getHorarioConclusao());

        return tarefaExistente;
    }

    @Transactional
    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }
}
