package com.rcbg.afku.investmentdiary.brokeraccounts.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerAccountRepository extends JpaRepository<BrokerAccount, Integer> {
}
