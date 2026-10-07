package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.dto.*;
import br.com.api.clearSpaces.entity.*;
import br.com.api.clearSpaces.repository.FuncionarioRepository;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PeriodoRepository periodoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PeriodoRepository periodoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.periodoRepository = periodoRepository;
    }

    @Transactional
    public void criar(FuncionarioDTO dto){
        Periodo periodoEncontrado = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + dto.getPeriodoId()));
        Funcionario novoFuncionario = new Funcionario(dto.getNome(), dto.getCpf(), dto.getSenha(), dto.getFuncao(),periodoEncontrado);
        funcionarioRepository.save(novoFuncionario);
    }

    public List<Funcionario> ler(){
        return funcionarioRepository.findAll();
    }

    @Transactional
    public void atualizar(Long id, FuncionarioDTO dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));

        Periodo novoPeriodo = periodoRepository.findById(dto.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Período não encontrado com o ID: " + dto.getPeriodoId()));

        if (dto.getSenhaAntiga().equals(funcionarioExistente.getSenha())){
            funcionarioExistente.setNome(dto.getNome());
            funcionarioExistente.setCpf(dto.getCpf());
            funcionarioExistente.setSenha(dto.getSenha());
            funcionarioExistente.setFuncao(dto.getFuncao());
            funcionarioExistente.setPeriodo(novoPeriodo);
        }else {
            throw new RuntimeException("Senha antiga inválida");
        }
    }

    @Transactional
    public void deletar(Long id){
        funcionarioRepository.deleteById(id);
    }

    public Optional<Funcionario> acharPorCpf(String cpf){
        return funcionarioRepository.findByCpf(cpf);
    }

    public Optional<Funcionario> acharPorMatricula(String matricula){
        return funcionarioRepository.findByMatricula(matricula);
    }

    public ResponseEntity<?> login(LoginDTO dto, HttpSession session){
        Optional<Funcionario> funcionarioCpfTmp = acharPorCpf(dto.getCpf());
        Optional<Funcionario> funcionarioMatriculaTmp = acharPorMatricula(dto.getMatricula());

        if (funcionarioCpfTmp.isPresent()){
            session.setAttribute("nome", funcionarioCpfTmp.get().getNome());
            session.setAttribute("funcao", funcionarioCpfTmp.get().getFuncao());
            if (dto.getSenha().equals(funcionarioCpfTmp.get().getSenha())){
                Funcionario funcionarioAchado = funcionarioCpfTmp.get();
                return ResponseEntity.ok(funcionarioAchado);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }else if (funcionarioMatriculaTmp.isPresent()){
            session.setAttribute("nome", funcionarioMatriculaTmp.get().getNome());
            session.setAttribute("funcao", funcionarioMatriculaTmp.get().getFuncao());
            if (dto.getSenha().equals(funcionarioMatriculaTmp.get().getSenha())){
                Funcionario funcionarioAchado = funcionarioMatriculaTmp.get();
                return ResponseEntity.ok(funcionarioAchado);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Funcionario inexistente");
        }
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Saiu com sucesso");
    }

    public ResponseEntity<String> funcionarioLogado(HttpSession session) {
        String nome = (String) session.getAttribute("nome");
        if (nome != null){
            return ResponseEntity.ok("Funcionário logado: " + nome);
        }else{
            return ResponseEntity.ok("Nenhum usuario logado! ");
        }
    }

    @Transactional
    public ResponseEntity<String> redefinirSenhaDireta(RecuperacaoDTO dto) {
        Optional<Funcionario> funcionarioOpt = Optional.empty();

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            funcionarioOpt = funcionarioRepository.findByCpf(dto.getCpf());
        } else if (dto.getMatricula() != null && !dto.getMatricula().isBlank()) {
            funcionarioOpt = funcionarioRepository.findByMatricula(dto.getMatricula());
        }

        if (funcionarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }

        Funcionario funcionario = funcionarioOpt.get();
        funcionario.setSenha(dto.getNovaSenha());
        funcionarioRepository.save(funcionario);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
}
