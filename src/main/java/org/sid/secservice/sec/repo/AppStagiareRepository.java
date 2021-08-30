package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Stagiaire;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppStagiareRepository extends PagingAndSortingRepository<Stagiaire, Long> {
}
