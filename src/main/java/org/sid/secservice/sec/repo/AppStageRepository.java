package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Stage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppStageRepository extends PagingAndSortingRepository<Stage, Long> {
}
