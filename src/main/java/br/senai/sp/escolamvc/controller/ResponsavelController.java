package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Responsavel;
import br.senai.sp.escolamvc.repository.ResponsavelRepository;
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
@RequestMapping("/responsavel")

public class ResponsavelController {
    @Autowired
    private ResponsavelRepository responsavelRepository;

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null){
            return "/responsavel/listagem";
        }

        List<Responsavel> responsaveisPesquisados = responsavelRepository.findResponsavelsByNomeContaining(nome);

        if (responsaveisPesquisados.isEmpty()){
            return "/responsavel/listagem";
        }


        model.addAttribute("alunos", responsaveisPesquisados);
        return "/responsavel/listagem";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid Responsavel responsavel, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()) {
            if (responsavel.getId() != null){
                return "/responsavel/alterar";
            }
            return "responsavel/inserir";
        }

        responsavelRepository.save(responsavel);
        attributes.addFlashAttribute("mensagem", "Responsável Cadastrado com Sucesso");
        return "redirect:/responsavel/novo";
    }

    @GetMapping("/novo")
    public String novo(Model model){
        model.addAttribute("responsavel", new Responsavel());
        return "responsavel/inserir";
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("responsáveis", responsavelRepository.findAll());
        return "responsavel/listagem";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        responsavelRepository.delete(responsavel);

        attributes.addFlashAttribute("mensagem",
                "Responsável excluído com sucesso!");

        return "redirect:/responsavel";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("responsavel", responsavel);

        return "responsavel/alterar";
    }
}
