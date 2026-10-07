package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.*;
import br.com.api.clearSpaces.entity.Funcionario;
import br.com.api.clearSpaces.entity.Professor;
import br.com.api.clearSpaces.service.FuncionarioService;
import br.com.api.clearSpaces.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@CrossOrigin("*")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO dto, HttpSession session) {
        return professorService.login(dto, session);
    }

    @PostMapping("/registrar")
    public void criar(@RequestBody ProfessorDTO dto) {
        professorService.criar(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        return professorService.logout(session);
    }

    @GetMapping()
    public ResponseEntity<String> funcionarioLogado(HttpSession session) {
        return professorService.professorLogado(session);
    }

    @GetMapping("/listar")
    public List<Professor> listar() {
        return professorService.ler();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody ProfessorDTO dto) {
        professorService.atualizar(id, dto);
    }

    @PutMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody RecuperacaoDTO dto) {
        return professorService.redefinirSenhaDireta(dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        professorService.deletar(id);
    }
}