package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/professor")

public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null){
            return "/professor/listagem";
        }

        List<Professor> professoresPesquisados = professorRepository.findProfessorsByNomeContaining(nome);

        if (professoresPesquisados.isEmpty()){
            return "/professor/listagem";
        }


        model.addAttribute("alunos", professoresPesquisados);
        return "/professor/listagem";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid Professor professor, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()) {
            if (professor.getId() != null){
                return "/professor/alterar";
            }
            return "professor/inserir";
        }

        professorRepository.save(professor);
        attributes.addFlashAttribute("mensagem", "Professor Cadastrado com Sucesso");
        return "redirect:/professor/novo";
    }

    @GetMapping("/novo")
    public String novo(Model model){
        model.addAttribute("professor", new Professor());
        return "professor/inserir";
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("professores", professorRepository.findAll());
        return "professor/listagem";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        professorRepository.delete(professor);

        attributes.addFlashAttribute("mensagem",
                "Professor excluído com sucesso!");

        return "redirect:/professor";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("professor", professor);

        return "professor/alterar";
    }
}
