package org.sid.secservice.sec.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 140, message = "Les username doivent comporter entre {min} et {max} caractères.")
    @NotBlank(message = "Le username est obligatoire")
    private String username;
    //Pour ne pas afficher password au format json
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // A chak fois kon charge utilisateur automatiquement il va charger role
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles = new ArrayList<>();
}

