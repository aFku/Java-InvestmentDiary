package com.rcbg.afku.investmentdiary.brokeraccounts.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findAllByProvider(String provider, Pageable pageable);
    Page<Account> findAllByAccountId(String accountId, Pageable pageable);
    Page<Account> findAllByCreationDate(Date date, Pageable pageable);
    Page<Account> findAllByCreationDateBetween(Date start, Date stop, Pageable pageable);
}
