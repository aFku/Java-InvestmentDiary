package com.rcbg.afku.investmentdiary.marketsubjects.services;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectMapper;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.exceptions.MarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.marketsubjects.repositories.MarketSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarketSubjectBrowseService {

    private final MarketSubjectRepository repo;

    @Autowired
    public MarketSubjectBrowseService(MarketSubjectRepository repo) {
        this.repo = repo;
    }

    public MarketSubject getMarketSubjectDomainObjectById(int id){
        return repo.findById(id).orElseThrow( () -> new MarketSubjectNotFound("Market subject with id: " + id + " not found"));
    }

    public MarketSubjectDTO getMarketSubjectById(int id){
        MarketSubject marketSubject = getMarketSubjectDomainObjectById(id);
        return MarketSubjectMapper.INSTANCE.toDTO(marketSubject);
    }

    public CommonPaginationDTO getAllMarketSubjects(Pageable pageable){
        Page<MarketSubjectDTO> subjects = repo.findAll(pageable).map(MarketSubjectMapper.INSTANCE::toDTO);
        return PageableManagement.createPaginationDTO(subjects);
    }
}
