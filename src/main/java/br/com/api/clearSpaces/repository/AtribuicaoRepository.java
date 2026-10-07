package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Atribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao,Long> {
    List<Atribuicao> findByAmbienteId(Long ambienteId);
}
