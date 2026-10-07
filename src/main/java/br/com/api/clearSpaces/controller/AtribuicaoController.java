package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.AtribuicaoDTO;
import br.com.api.clearSpaces.entity.Atribuicao;
import br.com.api.clearSpaces.service.AtribuicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atribuicoes")
@CrossOrigin("*")
public class AtribuicaoController {

    private final AtribuicaoService atribuicaoService;

    public AtribuicaoController(AtribuicaoService atribuicaoService) {
        this.atribuicaoService = atribuicaoService;
    }

    @PostMapping
    public void criar(@RequestBody AtribuicaoDTO dto) {
        atribuicaoService.salvar(dto);
    }

    @GetMapping
    public List<Atribuicao> listar() {
        return atribuicaoService.ler();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody AtribuicaoDTO dto) {
        atribuicaoService.atualizar(id, dto);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<?> concluir(@PathVariable Long id) {
        try {
            Atribuicao atribuicao = atribuicaoService.concluir(id);
            return ResponseEntity.ok(atribuicao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        atribuicaoService.deletar(id);
    }
}