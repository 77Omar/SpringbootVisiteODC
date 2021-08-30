package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Apprenant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppApprenantRepository extends PagingAndSortingRepository<Apprenant, Long> {
}
