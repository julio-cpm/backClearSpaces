package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.*;
import br.com.api.clearSpaces.entity.*;
import br.com.api.clearSpaces.repository.FuncionarioRepository;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PeriodoRepository periodoRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              PeriodoRepository periodoRepository,
                              PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.periodoRepository = periodoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void criar(FuncionarioDTO dto) {
        Periodo periodoEncontrado = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        Funcionario novoFuncionario = new Funcionario(dto.getNome(), dto.getCpf(), senhaCriptografada, dto.getFuncao(), periodoEncontrado);
        funcionarioRepository.save(novoFuncionario);
    }

    public List<Funcionario> ler() {
        return funcionarioRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, FuncionarioDTO dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        Periodo novoPeriodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        if (passwordEncoder.matches(dto.getSenhaAntiga(), funcionarioExistente.getSenha())) {
            funcionarioExistente.setNome(dto.getNome());
            funcionarioExistente.setCpf(dto.getCpf());
            if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
                funcionarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
            funcionarioExistente.setFuncao(dto.getFuncao());
            funcionarioExistente.setPeriodo(novoPeriodo);
        } else {
            throw new RuntimeException("Senha antiga inválida");
        }
    }

    @Transactional
    public void deletar(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public Optional<Funcionario> acharPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }

    public Optional<Funcionario> acharPorMatricula(String matricula) {
        return funcionarioRepository.findByMatricula(matricula);
    }

    public ResponseEntity<?> login(LoginDTO dto, HttpSession session) {
        Optional<Funcionario> funcionarioOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            funcionarioOpt = acharPorCpf(dto.getCpf());
        }

        if (funcionarioOpt.isEmpty() && dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            funcionarioOpt = acharPorMatricula(dto.getMatricula());
        }

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            if (passwordEncoder.matches(dto.getSenha(), funcionario.getSenha())) {
                session.setAttribute("nome", funcionario.getNome());
                session.setAttribute("funcao", funcionario.getFuncao());
                return ResponseEntity.ok(funcionario);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Funcionário inexistente");
        }
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Saiu com sucesso");
    }

    public ResponseEntity<String> funcionarioLogado(HttpSession session) {
        String nome = (String) session.getAttribute("nome");
        if (nome != null) {
            return ResponseEntity.ok("Funcionário logado: " + nome);
        } else {
            return ResponseEntity.ok("Nenhum usuário logado!");
        }
    }

    @Transactional
    public ResponseEntity<String> redefinirSenhaDireta(RecuperacaoDTO dto) {
        Optional<Funcionario> funcionarioOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            funcionarioOpt = funcionarioRepository.findByCpf(dto.getCpf());
        }

        if (funcionarioOpt.isEmpty() && dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            funcionarioOpt = funcionarioRepository.findByMatricula(dto.getMatricula());
        }

        if (funcionarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }

        Funcionario funcionario = funcionarioOpt.get();
        funcionario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        funcionarioRepository.save(funcionario);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
}
