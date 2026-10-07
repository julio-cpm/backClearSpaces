package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.dto.OcorrenciaDTO;
import br.com.api.clearSpaces.entity.Ocorrencia;
import br.com.api.clearSpaces.service.OcorrenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
@CrossOrigin("*")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public Ocorrencia criar(@RequestBody OcorrenciaDTO dto) {
        return ocorrenciaService.salvar(dto);
    }

    @GetMapping
    public List<Ocorrencia> listar() {
        return ocorrenciaService.listarTodas();
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public List<Ocorrencia> listarPorFuncionario(@PathVariable Long funcionarioId) {
        return ocorrenciaService.listarPorFuncionario(funcionarioId);
    }

    @PutMapping("/{id}")
    public Ocorrencia atualizar(@PathVariable Long id, @RequestBody OcorrenciaDTO dto) {
        return ocorrenciaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/designar")
    public Ocorrencia designar(@PathVariable Long id, @RequestBody OcorrenciaDTO dto) {
        return ocorrenciaService.designar(id, dto.getEncarregadoId());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ocorrenciaService.deletar(id);
    }
}