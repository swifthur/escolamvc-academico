package br.senai.sp.escolamvc.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue(value = "P")
@Entity
public class Professor extends Pessoa {
}