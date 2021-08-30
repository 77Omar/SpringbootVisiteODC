package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Visiteur;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppVisiteurRepository extends PagingAndSortingRepository<Visiteur, Long> {
    Visiteur findByCni(Double cni);
}
