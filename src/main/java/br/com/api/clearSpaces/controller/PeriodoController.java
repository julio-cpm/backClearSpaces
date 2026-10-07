package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.service.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periodos")
@CrossOrigin("*")
public class PeriodoController {

    private final PeriodoService periodoService;

    public PeriodoController(PeriodoService service){
        this.periodoService = service;
    }

    @GetMapping
    public List<Periodo> listar() {
        return periodoService.listarTodos();
    }
}