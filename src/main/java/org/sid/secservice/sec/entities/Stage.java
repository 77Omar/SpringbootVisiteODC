package org.sid.secservice.sec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    // A chak fois kon charge utilisateur automatiquement il va charger role
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Stagiaire> stagiaires = new ArrayList<>();
}
