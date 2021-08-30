package org.sid.secservice.sec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_PERSONNE")
@DiscriminatorValue("parent")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Size(min = 3, max = 140, message = "Les prenoms doivent comporter entre {min} et {max} caractères.")
    @NotBlank(message = "Le prenom est obligatoire")
    protected String prenom;
    @Size(min = 2, max = 140, message = "Les noms doivent comporter entre {min} et {max} caractères.")
    @NotBlank(message = "Le nom est obligatoire")
    protected String nom;
}
