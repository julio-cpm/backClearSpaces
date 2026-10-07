package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.AmbienteDTO;
import br.com.api.clearSpaces.dto.OcupacaoAmbienteDTO;
import br.com.api.clearSpaces.entity.Ambiente;
import br.com.api.clearSpaces.service.AmbienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambientes")
@CrossOrigin("*")
public class AmbienteController {

    private final AmbienteService ambienteService;

    public AmbienteController(AmbienteService ambienteService) {
        this.ambienteService = ambienteService;
    }

    @PostMapping
    public void criar(@RequestBody AmbienteDTO dto) {
        ambienteService.criar(dto);
    }

    @GetMapping
    public List<Ambiente> listar() {
        List<Ambiente> lista = ambienteService.ler();
        return lista;
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody AmbienteDTO dto) {
        ambienteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ambienteService.deletar(id);
    }

    @PutMapping("/{id}/ocupar")
    public ResponseEntity<?> ocupar(@PathVariable Long id, @RequestBody OcupacaoAmbienteDTO dto) {
        try {
            Ambiente ambiente = ambienteService.ocupar(id, dto.getProfessorId());
            return ResponseEntity.ok(ambiente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<?> liberar(@PathVariable Long id) {
        try {
            Ambiente ambiente = ambienteService.liberar(id);
            return ResponseEntity.ok(ambiente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Ambiente> buscarPorProfessor(@PathVariable Long professorId) {
        return ambienteService.buscarPorProfessor(professorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }
}