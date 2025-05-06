package br.senai.sp.escolamvc.api;

import br.senai.sp.escolamvc.model.Tarefa;
import br.senai.sp.escolamvc.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Tarefa> listar() {
        return tarefaRepository.findAll();
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public Tarefa salvar(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public void editar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        Tarefa tarefaBanco = tarefaRepository.findById(id).get();

        if (tarefa.getTitulo() != null) {
            tarefaBanco.setTitulo(tarefa.getTitulo());
        }
        if (tarefa.getDescricao() != null) {
            tarefaBanco.setDescricao(tarefa.getDescricao());
        }
        if (tarefa.getPrazo() != null) {
            tarefaBanco.setPrazo(tarefa.getPrazo());
        }
        if (tarefa.isConcluida()){
            tarefaBanco.setConcluida(true);
        }

        tarefaRepository.save(tarefaBanco);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public void excluir(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
    }
}