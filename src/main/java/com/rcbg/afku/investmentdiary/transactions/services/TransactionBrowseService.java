package com.rcbg.afku.investmentdiary.transactions.services;

import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.PaginationStockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.PaginationStockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import com.rcbg.afku.investmentdiary.transactions.repositories.StockMarketTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionBrowseService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionBrowseService.class);

    @Autowired
    private final StockMarketTransactionRepository repo;

    public TransactionBrowseService(StockMarketTransactionRepository repo) {
        this.repo = repo;
    }

    public StockMarketTransaction getStockMarketTransactionDomainObjectById(long id){
        return repo.findById(id).orElseThrow( () -> new StockMarketSubjectNotFound("Transaction with id: " + id + " not found"));
    }

    public StockMarketTransactionDTO findOneTransactionById(long id){
        return new StockMarketTransactionDTO(getStockMarketTransactionDomainObjectById(id));
    }

    public PaginationStockMarketTransactionDTO findAllTransaction(Pageable pageable){
        Page<StockMarketTransaction> transactions = repo.findAll(pageable);
        return createPaginationResponse(transactions);
    }

    private List<StockMarketTransactionDTO> convertListOfTransactionsToListOfDTOS(List<StockMarketTransaction> subjects){
        return subjects.stream().map(StockMarketTransactionDTO::new).collect( Collectors.toList());
    }

    private PaginationStockMarketTransactionDTO createPaginationResponse(Page<StockMarketTransaction> transactions){
        int page = transactions.getNumber();
        if( page >= transactions.getTotalPages()){
            throw new IllegalArgumentException("There are no page with index " + page + " for this resource. Last index is " + (transactions.getTotalPages() - 1));
        }
        ArrayList<StockMarketTransactionDTO> data = (ArrayList<StockMarketTransactionDTO>) convertListOfTransactionsToListOfDTOS(transactions.getContent());
        return new PaginationStockMarketTransactionDTO(page, transactions.getTotalPages(), transactions.getSize(),
                transactions.getTotalElements(), transactions.isLast(), data);
    }
}
