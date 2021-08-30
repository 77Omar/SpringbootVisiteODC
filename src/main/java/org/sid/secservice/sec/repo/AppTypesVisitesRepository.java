package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.TypeVisites;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTypesVisitesRepository extends PagingAndSortingRepository<TypeVisites, Long> {
}
