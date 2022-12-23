package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountMapper;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrokerAccountBrowseService {

    private final BrokerAccountRepository repo;

    @Autowired
    public BrokerAccountBrowseService(BrokerAccountRepository brokerAccountRepository){
        this.repo = brokerAccountRepository;
    }

    public BrokerAccountDTO findOneBrokerAccountById(int id){
        return BrokerAccountMapper.INSTANCE.toDTO(getBrokerAccountDomainObjectById(id));
    }

    public CommonPaginationDTO findAllBrokerAccounts(Pageable pageable){
        Page<BrokerAccountDTO> accounts = repo.findAll(pageable).map(BrokerAccountMapper.INSTANCE::toDTO);
        return PageableManagement.createPaginationDTO(accounts);
    }

    public BrokerAccount getBrokerAccountDomainObjectById(int id){
        return repo.findById(id).orElseThrow( () -> new BrokerAccountNotFoundException("Broker account with id: " + id + " not found"));
    }
}
