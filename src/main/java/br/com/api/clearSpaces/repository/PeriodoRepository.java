package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo,Long> {
}
