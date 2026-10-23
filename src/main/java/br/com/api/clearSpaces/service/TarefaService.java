package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.TarefaDTO;
import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import br.com.api.clearSpaces.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

        if (dto.getProcedimentos() != null && !dto.getProcedimentos().isEmpty()) {
            novaTarefa.setProcedimentos(dto.getProcedimentos());
        } else {
            novaTarefa.setProcedimentos(List.of(
                "Lavagem de vasos",
                "Higienização de pias",
                "Reposição de papéis",
                "Recolhimento do lixo"
            ));
        }

        this.tarefaRepository.save(novaTarefa);
        return novaTarefa;
    }

    @Transactional
    public Tarefa salvarFotoAntes(Long id, TarefaDTO dto) {
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        tarefaEncontrada.setFoto_antes(dto.getFoto_antes());

        this.tarefaRepository.save(tarefaEncontrada);
        return tarefaEncontrada;
    }

    @Transactional
    public Tarefa salvarFotoDepois(Long id, TarefaDTO dto) {
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        tarefaEncontrada.setFoto_depois(dto.getFoto_depois());

        this.tarefaRepository.save(tarefaEncontrada);
        return tarefaEncontrada;
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

        if (dto.getProcedimentos() != null && !dto.getProcedimentos().isEmpty()) {
            tarefaExistente.setProcedimentos(dto.getProcedimentos());
        }

        return tarefaExistente;
    }

    @Transactional
    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }
}
