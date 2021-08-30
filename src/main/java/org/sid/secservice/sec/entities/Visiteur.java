package org.sid.secservice.sec.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("visiteur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_Visite")
public class Visiteur extends Personne {

    // @Pattern(regexp = "[0-9]{13}$", message = "Les cni doivent comporter {min} caractères.")
    @NotNull
    protected Double cni;
    @JsonFormat(pattern="yyyy-MM-dd")
    protected Date dateDebutValide;
    @JsonFormat(pattern="yyyy-MM-dd")
    protected Date dateFinValide;
    @NotNull(message = "Merci de renseigner une lieu Delivrance svp.")
    protected String lieuDelivrance;

//LAZY: Visite seront chargés que si on souhaite
   // @OneToMany(mappedBy = "visiteur")
   //protected Collection<Visite> visites;
}
