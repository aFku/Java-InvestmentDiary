package com.rcbg.afku.investmentdiary.unit.brokeraccounts;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountManagementServiceTest {
    @Mock
    BrokerAccountRepository repo;

    @Mock
    BrokerAccountBrowseService serviceBrowse;

    @InjectMocks
    BrokerAccountManagementService managementService;

    @Test
    void testCreateAccountSuccessful(){
        BrokerAccountDTO createDto = new BrokerAccountDTO();
        createDto.setProvider("testProvider1");
        createDto.setAccountId("XCQ175");
        Mockito.when(repo.save(any(BrokerAccount.class))).thenAnswer(i -> i.getArguments()[0]);
        BrokerAccountDTO responseDto = managementService.createBrokerAccount(createDto);
        Assertions.assertEquals(responseDto.getAccountId(), responseDto.getAccountId());
        Assertions.assertEquals(responseDto.getProvider(), responseDto.getProvider());
    }
    @Test
    void testDeleteAccountSuccessful(){
        int id = 20;
        managementService.deleteBrokerAccount(id);
    }

    @Test
    void testDeleteAccountThatNotExist(){
        int id = 15;
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repo).deleteById(id);
        Assertions.assertThrows(BrokerAccountNotFoundException.class, () -> managementService.deleteBrokerAccount(id));
    }

    @Test
    void testUpdateAccountThatNotExist(){
        int id = 30;
        Mockito.doThrow(new BrokerAccountNotFoundException("Broker account with id: 30 not found")).when(serviceBrowse).getBrokerAccountDomainObjectById(id);
        BrokerAccountDTO updateDto = new BrokerAccountDTO();
        Assertions.assertThrows(BrokerAccountNotFoundException.class, () -> managementService.updateAccount(30, updateDto));
    }

    @Test
    void testUpdateAccountSuccessful(){
        int id = 1;
        BrokerAccount originalAccount = new BrokerAccount();
        originalAccount.setAccountId("XGD105");
        originalAccount.setProvider("testProvider1");
        BrokerAccountDTO requestDTO = new BrokerAccountDTO();
        requestDTO.setAccountId("KLQ743");
        requestDTO.setProvider("testProvider2");
        Mockito.when(serviceBrowse.getBrokerAccountDomainObjectById(id)).thenReturn(originalAccount);
        Mockito.when(repo.save(any(BrokerAccount.class))).thenAnswer(i -> i.getArguments()[0]);
        BrokerAccountDTO responseDTO = managementService.updateAccount(id, requestDTO);
        Assertions.assertEquals(responseDTO.getId(), originalAccount.getId());
        Assertions.assertEquals(responseDTO.getAccountId(), "KLQ743");
        Assertions.assertEquals(responseDTO.getProvider(), "testProvider2");
    }
}
