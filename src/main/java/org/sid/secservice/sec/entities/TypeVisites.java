package org.sid.secservice.sec.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeVisites {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;

    // @OneToMany(mappedBy = "typeVisites", fetch = FetchType.LAZY)
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   // private Collection<Visite> visites;
}
