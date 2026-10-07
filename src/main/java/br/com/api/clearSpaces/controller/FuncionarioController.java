package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.*;
import br.com.api.clearSpaces.entity.Funcionario;
import br.com.api.clearSpaces.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin("*")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO dto, HttpSession session) {
        return funcionarioService.login(dto, session);
    }

    @PostMapping("/registrar")
    public void criar(@RequestBody FuncionarioDTO dto) {
        funcionarioService.criar(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        return funcionarioService.logout(session);
    }

    @GetMapping()
    public ResponseEntity<String> funcionarioLogado(HttpSession session) {
        return funcionarioService.funcionarioLogado(session);
    }

    @GetMapping("/listar")
    public List<Funcionario> listar() {
        return funcionarioService.ler();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        funcionarioService.atualizar(id, dto);
    }

    @PutMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody RecuperacaoDTO dto) {
        return funcionarioService.redefinirSenhaDireta(dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
    }
}