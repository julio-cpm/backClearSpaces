package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findByRemetenteId(Long funcionarioId);

    List<Ocorrencia> findByStatus(Ocorrencia.StatusEnum status);
    
    @Modifying
    @Query("UPDATE Ocorrencia o SET o.remetente = NULL WHERE o.remetente.id = :id")
    void desvincularRemetente(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Ocorrencia o SET o.encarregado = NULL WHERE o.encarregado.id = :id")
    void desvincularEncarregado(@Param("id") Long id);
}
