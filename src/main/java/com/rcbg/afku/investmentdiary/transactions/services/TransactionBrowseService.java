package com.rcbg.afku.investmentdiary.transactions.services;

import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionMapper;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import com.rcbg.afku.investmentdiary.transactions.repositories.StockMarketTransactionRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionBrowseService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionBrowseService.class);


    private final StockMarketTransactionRepository repo;

    @Autowired
    public TransactionBrowseService(StockMarketTransactionRepository repo) {
        this.repo = repo;
    }

    public StockMarketTransaction getStockMarketTransactionDomainObjectById(long id){
        return repo.findById(id).orElseThrow( () -> new StockMarketSubjectNotFound("Transaction with id: " + id + " not found"));
    }

    public StockMarketTransactionDTO findOneTransactionById(long id){
        return StockMarketTransactionMapper.INSTANCE.toDTO(getStockMarketTransactionDomainObjectById(id));
    }

    public CommonPaginationDTO<StockMarketTransactionDTO> findAllTransaction(Pageable pageable){
        Page<StockMarketTransaction> transactions = repo.findAll(pageable);
        return PageableManagement.createPaginationDTO(transactions);
    }
}
