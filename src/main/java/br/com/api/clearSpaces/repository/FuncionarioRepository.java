package br.com.api.clearSpaces.repository;

import br.com.api.clearSpaces.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByMatricula(String matricula);
}
