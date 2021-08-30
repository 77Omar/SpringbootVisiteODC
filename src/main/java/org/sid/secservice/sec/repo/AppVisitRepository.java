package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Visite;
import org.sid.secservice.sec.entities.Visiteur;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppVisitRepository extends PagingAndSortingRepository<Visite, Long> {
    List<Visite> findByVisiteur(Visiteur visiteur);// cest comme celui la





/*
    @Query("SELECT v from  Visite v where function('day', v.dateEntree) = function('day',CURRENT_DATE)")
    List<Visite> findByVisite();

    @Query("SELECT v from  Visite v where function('day', v.dateEntree) = function('day',CURRENT_DATE) and v.visiteur.type = :type")
    List<Visite> findByVisiteurOfday(String type);
    */
}
