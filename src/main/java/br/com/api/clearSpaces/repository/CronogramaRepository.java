package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma,Long> {
}
