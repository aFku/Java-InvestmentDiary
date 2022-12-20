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
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class TransactionManagementService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementService.class);

    private final StockMarketTransactionRepository repo;
    private final AccountBrowseService accountBrowseService;
    private final StockMarketSubjectBrowseService stockMarketSubjectBrowseService;

    @Autowired
    public TransactionManagementService(StockMarketTransactionRepository transactionRepository, AccountBrowseService accountBrowseService, StockMarketSubjectBrowseService stockMarketSubjectBrowseService){
        this.repo = transactionRepository;
        this.accountBrowseService = accountBrowseService;
        this.stockMarketSubjectBrowseService = stockMarketSubjectBrowseService;
    }

    public StockMarketTransactionDTO createTransaction(@Valid StockMarketTransactionDTO dto){
        Account account = accountBrowseService.getAccountDomainObjectById(dto.getAccountId());
        StockMarketSubject subject = stockMarketSubjectBrowseService.getStockMarketSubjectDomainObjectById(dto.getSubjectId());
        StockMarketTransaction transaction = StockMarketTransactionMapper.INSTANCE.toEntity(dto, account, subject);
        repo.save(transaction);
        return StockMarketTransactionMapper.INSTANCE.toDTO(transaction);
    }

    public boolean deleteTransaction(long id){
        repo.deleteById(id);
        return true;
    }
}
