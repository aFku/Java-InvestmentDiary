package com.rcbg.afku.investmentdiary.brokeraccounts.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByProvider(String provider);
    List<Account> findAllByAccountId(String accountId);
    List<Account> findAllByCreationDate(Date date);
    List<Account> findAllByCreationDateBetween(Date start, Date stop);
}
