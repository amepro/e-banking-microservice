package com.senyitte.accountservices.repository;

import com.senyitte.accountservices.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);
    Optional<Account> findByAccountNumber(String accountNumber);
}
