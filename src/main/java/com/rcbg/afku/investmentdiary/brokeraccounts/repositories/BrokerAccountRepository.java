package com.rcbg.afku.investmentdiary.brokeraccounts.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrokerAccountRepository extends JpaRepository<BrokerAccount, Integer>, JpaSpecificationExecutor<BrokerAccount>{

}
