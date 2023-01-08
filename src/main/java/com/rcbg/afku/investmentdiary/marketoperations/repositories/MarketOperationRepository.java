package com.rcbg.afku.investmentdiary.marketoperations.repositories;

import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketOperationRepository extends JpaRepository<MarketOperation, Long>, JpaSpecificationExecutor<MarketOperation> {
}
