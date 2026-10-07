package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.OcorrenciaDTO;
import br.com.api.clearSpaces.entity.Funcionario;
import br.com.api.clearSpaces.entity.Ocorrencia;
import br.com.api.clearSpaces.repository.FuncionarioRepository;
import br.com.api.clearSpaces.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, FuncionarioRepository funcionarioRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Ocorrencia salvar(OcorrenciaDTO dto) {
        Funcionario remetente = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário remetente não encontrado"));

        Ocorrencia.GravidadeEnum gravidade = Ocorrencia.GravidadeEnum.valueOf(dto.getGravidade());

        Ocorrencia ocorrencia = new Ocorrencia(remetente, gravidade, dto.getMensagem());
        return ocorrenciaRepository.save(ocorrencia);
    }

    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    public List<Ocorrencia> listarPorFuncionario(Long funcionarioId) {
        return ocorrenciaRepository.findByRemetenteId(funcionarioId);
    }

    public Ocorrencia atualizar(Long id, OcorrenciaDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        if (dto.getGravidade() != null && !dto.getGravidade().isBlank()) {
            ocorrencia.setGravidade(Ocorrencia.GravidadeEnum.valueOf(dto.getGravidade()));
        }

        if (dto.getMensagem() != null && !dto.getMensagem().isBlank()) {
            ocorrencia.setMensagem(dto.getMensagem());
        }

        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia designar(Long id, Long encarregadoId) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        Funcionario encarregado = funcionarioRepository.findById(encarregadoId)
                .orElseThrow(() -> new RuntimeException("Funcionário encarregado não encontrado"));

        ocorrencia.setEncarregado(encarregado);
        ocorrencia.setStatus(Ocorrencia.StatusEnum.resolvido);

        return ocorrenciaRepository.save(ocorrencia);
    }

    public void deletar(Long id) {
        ocorrenciaRepository.deleteById(id);
    }
}