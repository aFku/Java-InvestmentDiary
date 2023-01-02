package com.rcbg.afku.investmentdiary.unit.marketoperations;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.entities.OperationType;
import com.rcbg.afku.investmentdiary.marketoperations.exceptions.MarketOperationNotFoundException;
import com.rcbg.afku.investmentdiary.marketoperations.repositories.MarketOperationRepository;
import com.rcbg.afku.investmentdiary.marketoperations.services.MarketOperationBrowseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MarketOperationBrowseServiceTest extends CommonUtilsMarketOperation {

    @Mock
    MarketOperationRepository repo;
    @InjectMocks
    MarketOperationBrowseService browseService;

    @Test
    void testFindOneOperationByIdExist(){
        MarketOperation testOperation = createRandomOperation();
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(testOperation));
        MarketOperationDTO dto = browseService.findOneMarketOperationById(1L);
        assertDTOWithOperationInstance(dto, testOperation);
    }

    @Test
    void testGetMarketOperationByIdNotExist(){
        Mockito.when(repo.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MarketOperationNotFoundException.class, () -> browseService.getStockMarketOperationDomainObjectById(2));
    }

    @Test
    void testGetAllMarketOperationsWithContent(){
        List<MarketOperation> operationList = new ArrayList<>();
        operationList.add(createRandomOperation());
        operationList.add(createRandomOperation());
        operationList.add(createRandomOperation());
        Pageable pageable = PageRequest.of(0, 3);
        Page<MarketOperation> page = new PageImpl<>(operationList, pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);

        CommonPaginationDTO operationsDTO = browseService.findAllMarketOperations(pageable);
        Assertions.assertEquals(operationsDTO.getSize(), operationList.size());
        for(int i=0; i < operationsDTO.getData().size(); i++){
            assertDTOWithOperationInstance((MarketOperationDTO) operationsDTO.getData().get(i), operationList.get(i));
        }
    }
    @Test
    void testGetAllMarketOperationsEmpty(){
        Pageable pageable = PageRequest.of(0, 3);
        Page<MarketOperation> page = new PageImpl<>(new ArrayList<>(), pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);
        Assertions.assertEquals(browseService.findAllMarketOperations(pageable).getData().size(), 0);
    }

    @Test
    void testGetMarketOperationDomainObject(){
        MarketOperation operation = new MarketOperation();
        operation.setOperationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        operation.setOperationType(OperationType.values()[random.nextInt(OperationType.values().length)]);
        operation.setPricePerOne(new BigDecimal(BigInteger.valueOf(random.nextInt(1000)), 2));
        operation.setVolume(random.nextInt(100));
        Mockito.when(repo.findById(5L)).thenReturn(Optional.of(operation));
        Assertions.assertEquals(operation, browseService.getStockMarketOperationDomainObjectById(5L));
    }

    @Test
    void testGetMarketOperationDomainObjectNotFound(){
        Mockito.when(repo.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MarketOperationNotFoundException.class, () -> browseService.getStockMarketOperationDomainObjectById(2L));
    }
}
