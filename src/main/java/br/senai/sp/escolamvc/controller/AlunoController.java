package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aluno")

public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null){
            return "/aluno/listagem";
        }

        List<Aluno> alunosPesquisados = alunoRepository.findAlunosByNomeContaining(nome);

        if (alunosPesquisados.isEmpty()){
            return "/aluno/listagem";
        }


        model.addAttribute("alunos", alunosPesquisados);
        return "/aluno/listagem";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Aluno aluno, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()) {
            if (aluno.getId() != null){
                return "/aluno/alterar";
            }

            return "aluno/inserir";
        }

        alunoRepository.save(aluno);
        attributes.addFlashAttribute("mensagem", "Aluno Cadastrado com Sucesso");
        return "redirect:/aluno/novo";
    }

    @GetMapping("/novo")
    public String novo(Model model){
        model.addAttribute("aluno", new Aluno());
        return "aluno/inserir";
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("alunos", alunoRepository.findAll());
        return "aluno/listagem";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        alunoRepository.delete(aluno);

        attributes.addFlashAttribute("mensagem",
                "Aluno excluído com sucesso!");

        return "redirect:/aluno";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("aluno", aluno);

        return "aluno/alterar";
    }
}
