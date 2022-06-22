package com.coderscampus.com.tim_smith_spring_assignment13.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.com.tim_smith_spring_assignment13.domain.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
}


