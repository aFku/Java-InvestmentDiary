package com.rcbg.afku.investmentdiary.brokeraccounts.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrokerAccountRepository extends JpaRepository<BrokerAccount, Integer>, JpaSpecificationExecutor<BrokerAccount>{

    @Query(value = "SELECT SUM((CASE WHEN market_operation.operation_type = 'BUY' THEN 1 ELSE -1 END) * market_operation.volume) AS 'volume', market_subject.name, market_operation.market_subject_id FROM market_operation INNER JOIN market_subject ON market_operation.market_subject_id = market_subject.id WHERE market_operation.broker_account_id = :accountId GROUP BY market_operation.market_subject_id", nativeQuery = true)
    Page<Object[]> getWalletById(@Param("accountId") int accountId, Pageable pageable);

    @Query(value = "SELECT SUM((CASE WHEN market_operation.operation_type = 'BUY' THEN 1 ELSE -1 END) * market_operation.volume) AS 'volume', market_subject.name, market_operation.market_subject_id, market_operation.broker_account_id FROM market_operation INNER JOIN market_subject ON market_operation.market_subject_id = market_subject.id GROUP BY market_operation.market_subject_id, market_operation.broker_account_id", nativeQuery = true)
    Page<Object[]> getWalletFromAllAccounts(Pageable pageable);
}
