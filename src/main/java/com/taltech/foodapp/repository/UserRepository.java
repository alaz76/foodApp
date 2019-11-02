package com.taltech.foodapp.repository;

import com.taltech.foodapp.dao.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByEmail(String email);
}
