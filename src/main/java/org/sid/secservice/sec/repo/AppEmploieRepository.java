package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.Employer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
public interface AppEmploieRepository extends PagingAndSortingRepository<Employer, Long> {
    Employer findByEmail(String email);
    void deleteById(Employer employer);
}
