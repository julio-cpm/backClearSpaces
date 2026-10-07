package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.AmbienteDTO;
import br.com.api.clearSpaces.entity.Ambiente;
import br.com.api.clearSpaces.entity.Atribuicao;
import br.com.api.clearSpaces.entity.Professor;
import br.com.api.clearSpaces.repository.AmbienteRepository;
import br.com.api.clearSpaces.repository.AtribuicaoRepository;
import br.com.api.clearSpaces.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmbienteService {

    private final AmbienteRepository repository;
    private final ProfessorRepository professorRepository;
    private final AtribuicaoRepository atribuicaoRepository;
    private final NotificacaoService notificacaoService;

    public AmbienteService(AmbienteRepository repository, ProfessorRepository professorRepository, NotificacaoService notificacaoService, AtribuicaoRepository atribuicaoRepository) {
        this.repository = repository;
        this.professorRepository = professorRepository;
        this.atribuicaoRepository = atribuicaoRepository;
        this.notificacaoService = notificacaoService;
    }

    @Transactional
    public void criar(AmbienteDTO ambienteDTO){
        repository.save(new Ambiente(ambienteDTO.getNome(),
                ambienteDTO.getTipo(),
                ambienteDTO.getLocalizacao(),
                ambienteDTO.getStatusAmbiente()));
    }

    public List<Ambiente> ler(){
        return repository.findAll();
    }

    @Transactional
    public void atualizar(Long id, AmbienteDTO ambienteDTO){
        Ambiente ambienteEncontrado = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + id));
        ambienteEncontrado.setNome(ambienteDTO.getNome());
        ambienteEncontrado.setTipo(ambienteDTO.getTipo());
        ambienteEncontrado.setLocalizacao(ambienteDTO.getLocalizacao());
        ambienteEncontrado.setStatusAmbiente(ambienteDTO.getStatusAmbiente());
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }

    @Transactional
    public Ambiente ocupar(Long ambienteId, Long professorId) {
        Ambiente ambiente = repository.findById(ambienteId)
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + ambienteId));

        if (ambiente.isOcupado()) {
            throw new RuntimeException("Este ambiente já está ocupado.");
        }

        Optional<Ambiente> jaOcupaOutro = repository.findByProfessorOcupanteId(professorId);
        if (jaOcupaOutro.isPresent()) {
            throw new RuntimeException("Você já está ocupando outra sala. Libere-a antes de ocupar uma nova.");
        }

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + professorId));

        ambiente.setOcupado(true);
        ambiente.setProfessorOcupante(professor);
        return repository.save(ambiente);
    }

    @Transactional
    public Ambiente liberar(Long ambienteId) {
        Ambiente ambiente = repository.findById(ambienteId)
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado com o ID: " + ambienteId));

        ambiente.setOcupado(false);
        ambiente.setProfessorOcupante(null);
        Ambiente ambienteSalvo = repository.save(ambiente);

        List<Atribuicao> atribuicoes = atribuicaoRepository.findByAmbienteId(ambienteId);

        for (Atribuicao atribuicao : atribuicoes) {
            Long funcionarioId = atribuicao.getFuncionario().getId();
            String mensagem = "O ambiente '" + ambiente.getNome() + "' foi liberado e está disponível.";

            notificacaoService.notificarFuncionario(funcionarioId, mensagem);
        }

        return ambienteSalvo;
    }

    public Optional<Ambiente> buscarPorProfessor(Long professorId) {
        return repository.findByProfessorOcupanteId(professorId);
    }
}