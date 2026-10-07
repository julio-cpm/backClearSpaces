package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente,Long> {
    Optional<Ambiente> findByProfessorOcupanteId(Long professorId);
}