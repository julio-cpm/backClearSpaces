package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.LoginDTO;
import br.com.api.clearSpaces.dto.ProfessorDTO;
import br.com.api.clearSpaces.dto.RecuperacaoDTO;
import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.entity.Professor;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import br.com.api.clearSpaces.repository.ProfessorRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PeriodoRepository periodoRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(ProfessorRepository professorRepository,
                            PeriodoRepository periodoRepository,
                            PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.periodoRepository = periodoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void criar(ProfessorDTO dto) {
        Periodo periodoEncontrado = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Professor novoProfessor = new Professor(
                dto.getNome(),
                limparCpf(dto.getCpf()),
                senhaCriptografada,
                periodoEncontrado
        );
        professorRepository.save(novoProfessor);
    }

    public List<Professor> ler() {
        return professorRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, ProfessorDTO dto) {
        Professor professorExistente = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + id));

        Periodo novoPeriodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        professorExistente.setNome(dto.getNome());
        professorExistente.setCpf(limparCpf(dto.getCpf()));

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            professorExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        professorExistente.setPeriodo(novoPeriodo);
    }

    @Transactional
    public void deletar(Long id) {
        professorRepository.deleteById(id);
    }

    public Optional<Professor> acharPorCpf(String cpf) {
        return professorRepository.findByCpf(limparCpf(cpf));
    }

    public Optional<Professor> acharPorMatricula(String matricula) {
        return professorRepository.findByMatricula(matricula);
    }

    public ResponseEntity<?> login(LoginDTO dto, HttpSession session) {
        Optional<Professor> professorOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            professorOpt = acharPorCpf(dto.getCpf());
        } else if (dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            professorOpt = acharPorMatricula(dto.getMatricula());
        }

        if (professorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Professor inexistente");
        }

        Professor professorAchado = professorOpt.get();

        if (passwordEncoder.matches(dto.getSenha(), professorAchado.getSenha())) {
            session.setAttribute("nome", professorAchado.getNome());
            return ResponseEntity.ok(professorAchado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Saiu com sucesso");
    }

    public ResponseEntity<String> professorLogado(HttpSession session) {
        String nome = (String) session.getAttribute("nome");
        if (nome != null) {
            return ResponseEntity.ok("Professor logado: " + nome);
        } else {
            return ResponseEntity.ok("Nenhum usuário logado!");
        }
    }

    @Transactional
    public ResponseEntity<String> redefinirSenhaDireta(RecuperacaoDTO dto) {
        Optional<Professor> professorOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            professorOpt = acharPorCpf(dto.getCpf());
        } else if (dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            professorOpt = acharPorMatricula(dto.getMatricula());
        }

        if (professorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado.");
        }

        Professor professor = professorOpt.get();
        professor.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        professorRepository.save(professor);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

    private String limparCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }
}
