package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.CronogramaDTO;
import br.com.api.clearSpaces.entity.Cronograma;
import br.com.api.clearSpaces.service.CronogramaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cronogramas")
@CrossOrigin("*")
public class CronogramaController {

    private final CronogramaService cronogramaService;

    public CronogramaController(CronogramaService cronogramaService) {
        this.cronogramaService = cronogramaService;
    }

    @PostMapping
    public void criar(@RequestBody CronogramaDTO dto) {
        cronogramaService.salvar(dto);
    }

    @GetMapping
    public List<Cronograma> listar() {
        return cronogramaService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody CronogramaDTO dto) {
        cronogramaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<?> concluir(@PathVariable Long id) {
        try {
            Cronograma cronograma = cronogramaService.concluir(id);
            return ResponseEntity.ok(cronograma);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cronogramaService.deletar(id);
    }
}