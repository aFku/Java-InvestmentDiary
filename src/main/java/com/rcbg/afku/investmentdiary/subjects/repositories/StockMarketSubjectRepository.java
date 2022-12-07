package com.rcbg.afku.investmentdiary.subjects.repositories;

import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMarketSubjectRepository extends JpaRepository<StockMarketSubject, Integer> {
}
