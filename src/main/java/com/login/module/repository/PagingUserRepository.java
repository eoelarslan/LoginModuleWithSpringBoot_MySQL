package com.login.module.repository;

import com.login.module.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ersin on 22.11.2019.
 */
@Repository
public interface PagingUserRepository extends PagingAndSortingRepository<User, Long> {
}
