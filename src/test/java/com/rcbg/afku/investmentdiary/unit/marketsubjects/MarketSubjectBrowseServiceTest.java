package com.rcbg.afku.investmentdiary.unit.marketsubjects;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.exceptions.MarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.marketsubjects.repositories.MarketSubjectRepository;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectBrowseService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MarketSubjectBrowseServiceTest {

    @Mock
    MarketSubjectRepository repo;

    @InjectMocks
    MarketSubjectBrowseService browseService;

    void assertDTOWithSubjectInstance(MarketSubjectDTO dto1, MarketSubject sbj){
        Assertions.assertEquals(dto1.getId(), sbj.getId());
        Assertions.assertEquals(dto1.getName(), sbj.getName());
        Assertions.assertEquals(dto1.getInfoSources(), sbj.getInfoSources());
        Assertions.assertEquals(dto1.isHasDividend(), sbj.isHasDividend());
    }

    @Test
    void testFindOneSubjectByIdExist(){
        MarketSubject testSubject = new MarketSubject();
        testSubject.setName("test_name");
        testSubject.setInfoSources("");
        testSubject.setHasDividend(true);

        Mockito.when(repo.findById(1)).thenReturn(Optional.of(testSubject));
        MarketSubjectDTO dto = browseService.getMarketSubjectById(1);
        assertDTOWithSubjectInstance(dto, testSubject);
    }

    @Test
    void testGetMarketSubjectByIdNotExist(){
        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(MarketSubjectNotFound.class, () -> browseService.getMarketSubjectById(2));
    }

    @Test
    void testGetAllMarketSubjectsWithContent(){
        List<MarketSubject> subjectList = new ArrayList<>();
        MarketSubject testSubject1 = new MarketSubject();
        testSubject1.setName("subject1");
        testSubject1.setInfoSources("internet");
        testSubject1.setHasDividend(true);
        subjectList.add(testSubject1);
        MarketSubject testSubject2 = new MarketSubject();
        testSubject2.setName("test_name");
        testSubject2.setInfoSources("");
        testSubject2.setHasDividend(false);
        subjectList.add(testSubject2);
        MarketSubject testSubject3 = new MarketSubject();
        testSubject3.setName("Company");
        testSubject3.setInfoSources("xxxx");
        testSubject3.setHasDividend(false);
        subjectList.add(testSubject3);
        Pageable pageable = PageRequest.of(0, 3);
        Page<MarketSubject> page = new PageImpl<>(subjectList, pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);

        CommonPaginationDTO subjectsDTO = browseService.getAllMarketSubjects(pageable);
        Assertions.assertEquals(subjectsDTO.getSize(), subjectList.size());
        for(int i=0; i < subjectsDTO.getData().size(); i++){
            assertDTOWithSubjectInstance((MarketSubjectDTO) subjectsDTO.getData().get(i), subjectList.get(i));
        }
    }
    @Test
    void testGetAllMarketSubjectsEmpty(){
        Pageable pageable = PageRequest.of(0, 3);
        Page<MarketSubject> page = new PageImpl<>(new ArrayList<>(), pageable, 3);
        Mockito.when(repo.findAll(pageable)).thenReturn(page);
        Assertions.assertEquals(browseService.getAllMarketSubjects(pageable).getData().size(), 0);
    }

    @Test
    void testGetMarketSubjectDomainObject(){
        MarketSubject testSubject = new MarketSubject();
        testSubject.setName("test_name");
        testSubject.setInfoSources("");
        testSubject.setHasDividend(false);
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(testSubject));
        Assertions.assertEquals(testSubject, browseService.getMarketSubjectDomainObjectById(1));
    }

    @Test
    void testGetMarketSubjectDomainObjectNotFound(){
        Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(MarketSubjectNotFound.class, () -> browseService.getMarketSubjectDomainObjectById(2));
    }
}
