package com.rcbg.afku.investmentdiary.unit.brokeraccounts;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AccountBrowseServiceTest {

    @Mock
    BrokerAccountRepository repo;

    @InjectMocks
    BrokerAccountBrowseService serviceBrowse;

    void assertDTOWithAccountInstance(BrokerAccountDTO dto1, BrokerAccount acc){
        Assertions.assertEquals(dto1.getId(), acc.getId());
        Assertions.assertEquals(dto1.getCreationDate(), acc.getCreationDate());
        Assertions.assertEquals(dto1.getProvider(), acc.getProvider());
        Assertions.assertEquals(dto1.getAccountId(), acc.getAccountId());
    }

    @Test
    void testFindOneAccountByIdExist(){
        BrokerAccount testAccount = new BrokerAccount();
        testAccount.setAccountId("XCQ175");
        testAccount.setProvider("testProvider");
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(testAccount));

        BrokerAccountDTO dto = serviceBrowse.findOneBrokerAccountById(1);
        assertDTOWithAccountInstance(dto, testAccount);
    }

    @Test
    void testFindOneAccountByIdNotExist(){
        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(BrokerAccountNotFoundException.class, () -> serviceBrowse.findOneBrokerAccountById(2));
    }

    @Test
    void testFindAllAccountsWithContent(){
        List<BrokerAccount> accountList = new ArrayList<>();
        BrokerAccount testAccount1 = new BrokerAccount();
        testAccount1.setAccountId("XCQ175");
        testAccount1.setProvider("testProvider1");
        accountList.add(testAccount1);
        BrokerAccount testAccount2 = new BrokerAccount();
        testAccount1.setAccountId("KJL785");
        testAccount1.setProvider("testProvider2");
        accountList.add(testAccount2);
        BrokerAccount testAccount3 = new BrokerAccount();
        testAccount1.setAccountId("WNS564");
        testAccount1.setProvider("testProvider3");
        accountList.add(testAccount3);
        Pageable pageable = PageRequest.of(0, 3);
        Page<BrokerAccount> page = new PageImpl<>(accountList, pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);

        CommonPaginationDTO accountsDTO = serviceBrowse.findAllBrokerAccounts(pageable);
        Assertions.assertEquals(accountsDTO.getSize(), accountList.size());
        for(int i=0; i < accountsDTO.getData().size(); i++){
            assertDTOWithAccountInstance((BrokerAccountDTO) accountsDTO.getData().get(i), accountList.get(i));
        }
    }
    @Test
    void testFindAllAccountsEmpty(){
        Pageable pageable = PageRequest.of(0, 3);
        Page<BrokerAccount> page = new PageImpl<>(new ArrayList<>(), pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);
        Assertions.assertEquals(serviceBrowse.findAllBrokerAccounts(pageable).getData().size(), 0);
    }

    @Test
    void getBrokerAccountDomainObject(){
        BrokerAccount testAccount = new BrokerAccount();
        testAccount.setAccountId("XCQ175");
        testAccount.setProvider("testProvider");
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(testAccount));
        Assertions.assertEquals(testAccount, serviceBrowse.getBrokerAccountDomainObjectById(1));
    }

    @Test
    void getBrokerAccountDomainObjectNotFound(){
        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(BrokerAccountNotFoundException.class, () -> serviceBrowse.getBrokerAccountDomainObjectById(2));
    }
}
