package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.CronogramaDTO;
import br.com.api.clearSpaces.entity.*;
import br.com.api.clearSpaces.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CronogramaService {

    private final CronogramaRepository cronogramaRepository;

    private final FuncionarioRepository funcionarioRepository;

    private final TarefaRepository tarefaRepository;

    private final AmbienteRepository ambienteRepository;

    public CronogramaService(CronogramaRepository cronogramaRepository, FuncionarioRepository funcionarioRepository, TarefaRepository tarefaRepository, AmbienteRepository ambienteRepository) {
        this.cronogramaRepository = cronogramaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.tarefaRepository = tarefaRepository;
        this.ambienteRepository = ambienteRepository;
    }

    @Transactional
    public void salvar(CronogramaDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + dto.getFuncionarioId()));

        Ambiente ambiente = ambienteRepository.findById(dto.getAmbienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + dto.getAmbienteId()));

        Tarefa tarefa = tarefaRepository.findById(dto.getTarefaId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + dto.getTarefaId()));

        Cronograma novoCronograma = new Cronograma(
                funcionario, ambiente, tarefa, dto.getDiaSemana(), dto.getHorarioInicio(), dto.getHorarioFim()
        );
        cronogramaRepository.save(novoCronograma);
    }

    public List<Cronograma> listarTodos() {
        return cronogramaRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, CronogramaDTO dto) {
        Cronograma cronogramaExistente = cronogramaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cronograma não encontrado com o ID: " + id));

        Funcionario novoFuncionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + dto.getFuncionarioId()));

        Ambiente novoAmbiente = ambienteRepository.findById(dto.getAmbienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + dto.getAmbienteId()));

        Tarefa novaTarefa = tarefaRepository.findById(dto.getTarefaId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + dto.getTarefaId()));

        cronogramaExistente.setFuncionario(novoFuncionario);
        cronogramaExistente.setAmbiente(novoAmbiente);
        cronogramaExistente.setTarefa(novaTarefa);
        cronogramaExistente.setDiaSemana(dto.getDiaSemana());
        cronogramaExistente.setHorarioInicio(dto.getHorarioInicio());
        cronogramaExistente.setHorarioFim(dto.getHorarioFim());
    }

    @Transactional
    public void deletar(Long id){
        cronogramaRepository.deleteById(id);
    }

    // Marca o cronograma como concluído agora (grava horarioConclusao) e
    // já deixa o Ambiente vinculado como "limpo", pra atualizar o
    // gráfico/anel de progresso do gerente imediatamente.
    @Transactional
    public Cronograma concluir(Long id) {
        Cronograma cronograma = cronogramaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cronograma não encontrado com o ID: " + id));

        cronograma.setHorarioConclusao(LocalDateTime.now());
        cronogramaRepository.save(cronograma);

        Ambiente ambiente = cronograma.getAmbiente();
        if (ambiente != null) {
            ambiente.setStatusAmbiente(Ambiente.StatusAmbienteEnum.limpo);
            ambienteRepository.save(ambiente);
        }

        return cronograma;
    }
}