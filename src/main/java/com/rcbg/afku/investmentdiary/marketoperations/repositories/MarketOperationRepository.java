package com.rcbg.afku.investmentdiary.marketoperations.repositories;

import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketOperationRepository extends JpaRepository<MarketOperation, Long> {
}
