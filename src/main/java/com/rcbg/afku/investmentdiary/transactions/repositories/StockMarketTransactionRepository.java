package com.rcbg.afku.investmentdiary.transactions.repositories;

import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMarketTransactionRepository extends JpaRepository<StockMarketTransaction, Long> {
}
