package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
