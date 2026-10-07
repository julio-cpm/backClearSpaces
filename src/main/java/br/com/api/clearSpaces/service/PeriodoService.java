package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.entity.Periodo;
import br.com.api.clearSpaces.repository.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodoService {

    private final PeriodoRepository repository;

    public PeriodoService(PeriodoRepository repository) {
        this.repository = repository;
    }

    public List<Periodo> listarTodos() {
        return repository.findAll();
    }
}
