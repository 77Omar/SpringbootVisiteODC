package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Formation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFormationRepository extends PagingAndSortingRepository<Formation, Long> {
}
