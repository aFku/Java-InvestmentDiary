package com.rcbg.afku.investmentdiary.unit.marketoperations;

import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationMapper;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.exceptions.MarketOperationNotFoundException;
import com.rcbg.afku.investmentdiary.marketoperations.repositories.MarketOperationRepository;
import com.rcbg.afku.investmentdiary.marketoperations.services.MarketOperationBrowseService;
import com.rcbg.afku.investmentdiary.marketoperations.services.MarketOperationManagementService;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.exceptions.MarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.marketsubjects.repositories.MarketSubjectRepository;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectBrowseService;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectManagementService;
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
public class MarketOperationManagementServiceTest extends CommonUtilsMarketOperation{

    @Mock
    MarketOperationRepository repo;

    @Mock
    BrokerAccountBrowseService browseServiceAccount;

    @Mock
    MarketSubjectBrowseService browseServiceSubject;

    @InjectMocks
    MarketOperationManagementService managementService;

    @Test
    void testCreateOperationSuccessful(){
        MarketOperation operation = createRandomOperation();
        MarketOperationDTO createDto = MarketOperationMapper.INSTANCE.toDTO(operation);
        Mockito.when(repo.save(any(MarketOperation.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(browseServiceAccount.getBrokerAccountDomainObjectById(createDto.getAccountId())).thenReturn(operation.getBrokerAccount());
        Mockito.when(browseServiceSubject.getMarketSubjectDomainObjectById(createDto.getSubjectId())).thenReturn(operation.getMarketSubject());
        MarketOperationDTO responseDto = managementService.createMarketOperation(createDto);
        assertDTOWithOperationInstance(responseDto, operation);
    }

    @Test
    void testDeleteOperationSuccessful(){
        long id = 20;
        managementService.deleteMarketOperation(id);
    }

    @Test
    void testDeleteOperationThatNotExist(){
        long id = 15;
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repo).deleteById(id);
        Assertions.assertThrows(MarketOperationNotFoundException.class, () -> managementService.deleteMarketOperation(id));
    }
}
