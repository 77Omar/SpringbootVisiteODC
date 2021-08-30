package org.sid.secservice.sec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("employer")
public class Employer extends Personne{

    @NotNull
    @Pattern(regexp = "7[7|6|8|0|5][0-9]{7}$", message = "Merci de renseigner un bon numero svp!")
    private String phone;
    @NotNull(message = "Merci de renseigner le email svp.")
    private String email;
    @NotNull(message = "Merci de renseigner le service svp.")
    private String service;

   // @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //private Collection<Visite> visites;

}
