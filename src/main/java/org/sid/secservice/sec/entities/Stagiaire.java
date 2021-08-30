package org.sid.secservice.sec.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("stagiaire")
public class Stagiaire extends Visiteur {

}
