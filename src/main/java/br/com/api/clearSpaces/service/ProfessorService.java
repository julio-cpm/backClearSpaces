package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.LoginDTO;
import br.com.api.clearSpaces.dto.RecuperacaoDTO;
import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.dto.ProfessorDTO;
import br.com.api.clearSpaces.entity.Professor;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import br.com.api.clearSpaces.repository.ProfessorRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final PeriodoRepository periodoRepository;

    public ProfessorService(ProfessorRepository professorRepository, PeriodoRepository periodoRepository) {
        this.professorRepository = professorRepository;
        this.periodoRepository = periodoRepository;
    }

    @Transactional
    public void criar(ProfessorDTO dto){
        Periodo periodoEncontrado = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + dto.getPeriodoId()));
        Professor novoProfessor = new Professor(dto.getNome(), dto.getCpf(), dto.getSenha(),periodoEncontrado);
        professorRepository.save(novoProfessor);
    }

    public List<Professor> ler(){
        return professorRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, ProfessorDTO dto) {
        Professor professorExistente = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        Periodo novoPeriodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        professorExistente.setNome(dto.getNome());
        professorExistente.setCpf(dto.getCpf());
        professorExistente.setSenha(dto.getSenha());
        professorExistente.setPeriodo(novoPeriodo);
    }

    @Transactional
    public void deletar(Long id){
        professorRepository.deleteById(id);
    }

    public Optional<Professor> acharPorCpf(String cpf){
        return professorRepository.findByCpf(cpf);
    }

    public Optional<Professor> acharPorMatricula(String matricula){
        return professorRepository.findByMatricula(matricula);
    }

    public ResponseEntity<?> login(LoginDTO dto, HttpSession session){
        Optional<Professor> professorCpfTmp = acharPorCpf(dto.getCpf());
        Optional<Professor> professorMatriculaTmp = acharPorMatricula(dto.getMatricula());

        if (professorCpfTmp.isPresent()){
            session.setAttribute("nome", professorCpfTmp.get().getNome());
            if (dto.getSenha().equals(professorCpfTmp.get().getSenha())){
                Professor professorAchado = professorCpfTmp.get();
                return ResponseEntity.ok(professorAchado);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }else if (professorMatriculaTmp.isPresent()){
            session.setAttribute("nome", professorMatriculaTmp.get().getNome());
            if (dto.getSenha().equals(professorMatriculaTmp.get().getSenha())){
                Professor professorAchado = professorMatriculaTmp.get();
                return ResponseEntity.ok(professorAchado);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Professor inexistente");
        }
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Saiu com sucesso");
    }

    public ResponseEntity<String> professorLogado(HttpSession session) {
        String nome = (String) session.getAttribute("nome");
        if (nome != null){
            return ResponseEntity.ok("Funcionário logado: " + nome);
        }else{
            return ResponseEntity.ok("Nenhum usuario logado! ");
        }
    }

    @Transactional
    public ResponseEntity<String> redefinirSenhaDireta(RecuperacaoDTO dto) {
        Optional<Professor> professorOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            professorOpt = professorRepository.findByCpf(dto.getCpf());
        } else if (dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            professorOpt = professorRepository.findByMatricula(dto.getMatricula());
        }

        if (professorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }

        Professor professor = professorOpt.get();
        professor.setSenha(dto.getNovaSenha());
        professorRepository.save(professor);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
}