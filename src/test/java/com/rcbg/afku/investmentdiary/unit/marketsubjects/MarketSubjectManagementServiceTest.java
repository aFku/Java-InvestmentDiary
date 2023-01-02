package com.rcbg.afku.investmentdiary.unit.marketsubjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
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
public class MarketSubjectManagementServiceTest {

    @Mock
    MarketSubjectRepository repo;

    @Mock
    MarketSubjectBrowseService browseService;

    @InjectMocks
    MarketSubjectManagementService managementService;

    @Test
    void testCreateSubjectSuccessful(){
        MarketSubjectDTO createDto = new MarketSubjectDTO();
        createDto.setName("test_name");
        createDto.setInfoSources("internet");
        createDto.setHasDividend(false);
        Mockito.when(repo.save(any(MarketSubject.class))).thenAnswer(i -> i.getArguments()[0]);
        MarketSubjectDTO responseDto = managementService.createMarketSubject(createDto);
        Assertions.assertEquals(responseDto.getName(), createDto.getName());
        Assertions.assertEquals(responseDto.getInfoSources(), createDto.getInfoSources());
        Assertions.assertEquals(responseDto.isHasDividend(), createDto.isHasDividend());
    }
    @Test
    void testDeleteSubjectSuccessful(){
        int id = 20;
        managementService.deleteMarketSubject(id);
    }

    @Test
    void testDeleteAccountThatNotExist(){
        int id = 15;
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repo).deleteById(id);
        Assertions.assertThrows(MarketSubjectNotFound.class, () -> managementService.deleteMarketSubject(id));
    }

    @Test
    void testUpdateSubjectThatNotExist(){
        int id = 30;
        Mockito.doThrow(new MarketSubjectNotFound("Market subject with id: 30 not found")).when(browseService).getMarketSubjectDomainObjectById(id);
        MarketSubjectDTO updateDto = new MarketSubjectDTO();
        Assertions.assertThrows(MarketSubjectNotFound.class, () -> managementService.updateMarketSubjectById(30, updateDto));
    }

    @Test
    void testUpdateSubjectSuccessful(){
        int id = 1;
        MarketSubject originalSubject = new MarketSubject();
        originalSubject.setName("test_name");
        originalSubject.setInfoSources("internet");
        originalSubject.setHasDividend(false);
        MarketSubjectDTO requestDTO = new MarketSubjectDTO();
        requestDTO.setName("newSubject");
        requestDTO.setInfoSources("no info");
        requestDTO.setHasDividend(true);
        Mockito.when(browseService.getMarketSubjectDomainObjectById(id)).thenReturn(originalSubject);
        Mockito.when(repo.save(any(MarketSubject.class))).thenAnswer(i -> i.getArguments()[0]);
        MarketSubjectDTO responseDTO = managementService.updateMarketSubjectById(id, requestDTO);
        Assertions.assertEquals(responseDTO.getId(), originalSubject.getId());
        Assertions.assertEquals(responseDTO.getName(), originalSubject.getName());
        Assertions.assertEquals(responseDTO.getInfoSources(), originalSubject.getInfoSources());
        Assertions.assertEquals(responseDTO.isHasDividend(), originalSubject.isHasDividend());
    }
}
