package org.sid.secservice.sec.entities;

import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@DiscriminatorValue("apprenant")

public class Apprenant  extends Visiteur{

}
