package org.sid.secservice.sec.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateEntree", nullable = false)
    private Date dateEntree= new Date();;
    private Date dateSortie;

    @ManyToOne
    @JoinColumn(name = "visiteur_Id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Visiteur visiteur;

    @ManyToOne
    @JoinColumn(name = "employer_Id")
    private Employer employer;

    @ManyToOne
    @JoinColumn(name = "typesVisite_Id")
    private TypeVisites typeVisites;

}
