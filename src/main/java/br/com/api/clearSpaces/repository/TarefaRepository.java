package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
    @Query(value = "SELECT * FROM tarefa WHERE (foto_antes IS NOT NULL AND LENGTH(foto_antes) > 0) OR (foto_depois IS NOT NULL AND LENGTH(foto_depois) > 0)", nativeQuery = true)
    List<Tarefa> buscarTarefasComFotos();
}