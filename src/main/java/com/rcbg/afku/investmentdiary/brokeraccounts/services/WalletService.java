package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.WalletRecordDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final BrokerAccountRepository repo;

    @Autowired
    public WalletService(BrokerAccountRepository repo){
        this.repo = repo;
    }

    public CommonPaginationDTO getWalletByBrokerAccountId(int accountId, Pageable pageable){
        if( ! repo.existsById(accountId) ) {throw new BrokerAccountNotFoundException("Broker account with ID: " + accountId + " not found");}
        Page<WalletRecordDTO> walletRecords = repo.getWalletById(accountId, pageable).map(WalletRecordDTO::new);
        return PageableManagement.createPaginationDTO(walletRecords);
    }

    public CommonPaginationDTO getWalletFromAllBrokerAccounts(Pageable pageable){
        Page<WalletRecordDTO> walletRecords = repo.getWalletFromAllAccounts(pageable).map(WalletRecordDTO::new);
        return PageableManagement.createPaginationDTO(walletRecords);
    }
}
