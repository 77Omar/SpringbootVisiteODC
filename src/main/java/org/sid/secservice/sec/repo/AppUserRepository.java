package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppUserRepository extends PagingAndSortingRepository<AppUser, Long> {
   AppUser findByUsername(String username);
}