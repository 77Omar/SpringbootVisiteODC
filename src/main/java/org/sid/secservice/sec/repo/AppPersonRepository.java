package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Personne;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppPersonRepository extends PagingAndSortingRepository<Personne, Long> {
    Personne findByNom(String nom);
}
