package br.senai.sp.escolamvc.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

@DiscriminatorValue(value = "A")
@Entity
public class Aluno extends Pessoa {
    @NotEmpty(message = "O campo Matrícula não pode ser vazio")
    private String matricula;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
