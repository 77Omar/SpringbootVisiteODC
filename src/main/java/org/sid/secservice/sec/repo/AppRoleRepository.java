package org.sid.secservice.sec.repo;

import org.sid.secservice.sec.entities.AppRole;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppRoleRepository extends PagingAndSortingRepository<AppRole, Long> {
   AppRole findByRoleName(String roleName);
}
