package com.rcbg.afku.investmentdiary.brokeraccounts.transactions.repositories;

import com.rcbg.afku.investmentdiary.brokeraccounts.transactions.entities.StockMarketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMarketTransactionRepository extends JpaRepository<StockMarketTransaction, Long> {
}
