package com.rcbg.afku.investmentdiary.marketoperations.services;

import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationMapper;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.exceptions.MarketOperationNotFoundException;
import com.rcbg.afku.investmentdiary.marketoperations.repositories.MarketOperationRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MarketOperationBrowseService {

    private final MarketOperationRepository repo;

    @Autowired
    public MarketOperationBrowseService(MarketOperationRepository repo) {
        this.repo = repo;
    }

    public MarketOperation getStockMarketOperationDomainObjectById(long id){
        return repo.findById(id).orElseThrow( () -> new MarketOperationNotFoundException("Market operation with id: " + id + " not found"));
    }

    public MarketOperationDTO findOneMarketOperationById(long id){
        return MarketOperationMapper.INSTANCE.toDTO(getStockMarketOperationDomainObjectById(id));
    }

    public CommonPaginationDTO findAllMarketOperations(Pageable pageable){
        Page<MarketOperationDTO> operations = repo.findAll(pageable).map(MarketOperationMapper.INSTANCE::toDTO);
        return PageableManagement.createPaginationDTO(operations);
    }

    public CommonPaginationDTO findAllMarketOperationsBySpecification(Pageable pageable, Specification<MarketOperation> spec){
        Page<MarketOperationDTO> operations = repo.findAll(spec, pageable).map(MarketOperationMapper.INSTANCE::toDTO);
        return PageableManagement.createPaginationDTO(operations);
    }
}
