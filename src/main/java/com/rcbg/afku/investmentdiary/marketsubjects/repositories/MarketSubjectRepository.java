package com.rcbg.afku.investmentdiary.marketsubjects.repositories;

import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MarketSubjectRepository extends JpaRepository<MarketSubject, Integer>, JpaSpecificationExecutor<MarketSubject> {
}
