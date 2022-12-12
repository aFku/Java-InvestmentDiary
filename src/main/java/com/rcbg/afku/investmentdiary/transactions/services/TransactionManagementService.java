package com.rcbg.afku.investmentdiary.transactions.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.services.StockMarketSubjectBrowseService;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionMapper;
import com.rcbg.afku.investmentdiary.transactions.entities.OperationType;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import com.rcbg.afku.investmentdiary.transactions.repositories.StockMarketTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagementService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementService.class);

    private final StockMarketTransactionRepository repo;
    private final AccountBrowseService accountBrowseService;
    private final StockMarketSubjectBrowseService stockMarketSubjectBrowseService;
    private final TransactionBrowseService browseService;


    @Autowired
    public TransactionManagementService(StockMarketTransactionRepository transactionRepository, AccountBrowseService accountBrowseService, StockMarketSubjectBrowseService stockMarketSubjectBrowseService, TransactionBrowseService browseService){
        this.repo = transactionRepository;
        this.accountBrowseService = accountBrowseService;
        this.stockMarketSubjectBrowseService = stockMarketSubjectBrowseService;
        this.browseService = browseService;
    }

    public StockMarketTransactionDTO createTransaction(StockMarketTransactionDTO dto){
//        Account account = accountBrowseService.getAccountDomainObjectById(dto.getAccountId());
//        StockMarketSubject subject = stockMarketSubjectBrowseService.getStockMarketSubjectDomainObjectById(dto.getSubjectId());
//        StockMarketTransaction transaction = new StockMarketTransaction();
//        transaction.setTransactionDate(dto.getTransactionDate());
//        transaction.setVolume(dto.getVolume());
//        transaction.setDescription(dto.getDescription());
//        transaction.setOperationType(OperationType.valueOf(dto.getOperationType()));
//        transaction.setPricePerOne(dto.getPricePerOne());
//        transaction.setAccount(account);
//        transaction.setSubject(subject);
        StockMarketTransaction transaction = StockMarketTransactionMapper.INSTANCE.stockMarketTransactionDTOToStockMarketTransaction(dto);
        repo.save(transaction);
        return StockMarketTransactionMapper.INSTANCE.stockMarketTransactionToStockMarketTransactionDTO(transaction);
    }

    public boolean deleteTransaction(long id){
        repo.deleteById(id);
        return true;
    }
}
