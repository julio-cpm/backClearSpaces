package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findByRemetenteId(Long funcionarioId);

    List<Ocorrencia> findByStatus(Ocorrencia.StatusEnum status);
}