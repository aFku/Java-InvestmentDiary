package com.rcbg.afku.investmentdiary.subjects.services;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.subjects.repositories.StockMarketSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockMarketSubjectBrowseService {

    private final StockMarketSubjectRepository repo;

    @Autowired
    public StockMarketSubjectBrowseService(StockMarketSubjectRepository repo) {
        this.repo = repo;
    }

    public StockMarketSubject getStockMarketSubjectDomainObjectById(int id){
        return repo.findById(id).orElseThrow( () -> new StockMarketSubjectNotFound("Subject with id: " + id + " not found"));
    }

    public StockMarketSubjectDTO getStockMarketSubjectById(int id){
        StockMarketSubject subject = getStockMarketSubjectDomainObjectById(id);
        return StockMarketSubjectMapper.INSTANCE.toDTO(subject);
        //return new StockMarketSubjectDTO(getStockMarketSubjectDomainObjectById(id));
    }

    public CommonPaginationDTO<StockMarketSubjectDTO> getAllStockMarketSubjects(Pageable pageable){
        Page<StockMarketSubject> subjects = repo.findAll(pageable);
        return PageableManagement.createPaginationDTO(subjects);
    }
}
