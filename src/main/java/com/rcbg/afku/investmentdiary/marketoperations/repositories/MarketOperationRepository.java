package com.rcbg.afku.investmentdiary.marketoperations.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarketOperationRepository extends JpaRepository<MarketOperation, Long>, JpaSpecificationExecutor<MarketOperation> {

    @Query(value = "SELECT SUM((CASE WHEN market_operation.operation_type = 'BUY' THEN 1 ELSE -1 END) * market_operation.volume) AS 'Volume' FROM market_operation WHERE market_operation.market_subject_id = :subjectId and market_operation.broker_account_id = :accountId", nativeQuery = true)
    Integer calculateNumberOfVolumesForAccount(@Param("accountId") int accountId, @Param("subjectId") int subjectId);

    Page<MarketOperation> findAllById(Pageable pageable, Specification<MarketOperation> specification);
}
