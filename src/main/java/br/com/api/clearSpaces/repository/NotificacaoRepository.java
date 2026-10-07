package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByFuncionarioIdOrderByDataHoraDesc(Long funcionarioId);
}