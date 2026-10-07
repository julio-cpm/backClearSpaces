package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.AtribuicaoDTO;
import br.com.api.clearSpaces.entity.*;
import br.com.api.clearSpaces.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtribuicaoService {

    private final AtribuicaoRepository atribuicaoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TarefaRepository tarefaRepository;
    private final AmbienteRepository ambienteRepository;

    public AtribuicaoService(AtribuicaoRepository atribuicaoRepository, FuncionarioRepository funcionarioRepository, TarefaRepository tarefaRepository, AmbienteRepository ambienteRepository) {
        this.atribuicaoRepository = atribuicaoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.tarefaRepository = tarefaRepository;
        this.ambienteRepository = ambienteRepository;
    }

    @Transactional
    public void salvar(AtribuicaoDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + dto.getFuncionarioId()));

        Tarefa tarefa = tarefaRepository.findById(dto.getTarefaId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + dto.getTarefaId()));

        Ambiente ambiente = ambienteRepository.findById(dto.getAmbienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + dto.getAmbienteId()));

        Atribuicao novaAtribuicao = new Atribuicao(funcionario, tarefa, ambiente, dto.getDia());
        novaAtribuicao.setDiaSemana(dto.getDiaSemana());
        novaAtribuicao.setHorarioInicio(dto.getHorarioInicio());
        novaAtribuicao.setHorarioFim(dto.getHorarioFim());
        atribuicaoRepository.save(novaAtribuicao);
    }

    public List<Atribuicao> ler(){
        return atribuicaoRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, AtribuicaoDTO dto) {
        Atribuicao atribuicaoExistente = atribuicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição não encontrada com o ID: " + id));

        Funcionario novoFuncionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + dto.getFuncionarioId()));

        Tarefa novaTarefa = tarefaRepository.findById(dto.getTarefaId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + dto.getTarefaId()));

        Ambiente novoAmbiente = ambienteRepository.findById(dto.getAmbienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + dto.getAmbienteId()));

        atribuicaoExistente.setFuncionario(novoFuncionario);
        atribuicaoExistente.setTarefa(novaTarefa);
        atribuicaoExistente.setAmbiente(novoAmbiente);
        atribuicaoExistente.setDia(dto.getDia());
        atribuicaoExistente.setDiaSemana(dto.getDiaSemana());
        atribuicaoExistente.setHorarioInicio(dto.getHorarioInicio());
        atribuicaoExistente.setHorarioFim(dto.getHorarioFim());
    }

    @Transactional
    public void deletar(Long id){
        atribuicaoRepository.deleteById(id);
    }

    @Transactional
    public Atribuicao concluir(Long id) {
        Atribuicao atribuicao = atribuicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição não encontrada com o ID: " + id));

        atribuicao.setHorarioConclusao(LocalDateTime.now());
        atribuicaoRepository.save(atribuicao);

        Ambiente ambiente = atribuicao.getAmbiente();
        if (ambiente != null) {
            ambiente.setStatusAmbiente(Ambiente.StatusAmbienteEnum.limpo);
            ambienteRepository.save(ambiente);
        }

        return atribuicao;
    }
}