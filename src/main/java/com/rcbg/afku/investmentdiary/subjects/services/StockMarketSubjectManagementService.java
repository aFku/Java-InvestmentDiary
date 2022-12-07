package com.rcbg.afku.investmentdiary.subjects.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.repositories.StockMarketSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StockMarketSubjectManagementService {

    private final StockMarketSubjectRepository repo;
    private final StockMarketSubjectBrowseService browseService;

    @Autowired
    public StockMarketSubjectManagementService(StockMarketSubjectRepository repo, StockMarketSubjectBrowseService browseService) {
        this.repo = repo;
        this.browseService = browseService;
    }

    public StockMarketSubjectDTO createStockMarketSubject(StockMarketSubjectDTO dto){
        StockMarketSubject subject = new StockMarketSubject();
        subject.setName(dto.getName());
        subject.setInfoSources(dto.getInfoSources());
        subject.setHasDividend(dto.isHasDividend());
        repo.save(subject);
        return new StockMarketSubjectDTO(subject);
    }

    public StockMarketSubjectDTO updateStockMarketSubjectById(int id, StockMarketSubjectDTO dto){
        StockMarketSubject subject = browseService.getStockMarketSubjectDomainObjectById(id);
        subject.setName(Objects.equals(dto.getName(), "") ? subject.getName(): dto.getName());
        subject.setHasDividend(Objects.equals(dto.isHasDividend(), null) ? subject.hasDividend(): dto.isHasDividend());
        subject.setInfoSources(Objects.equals(dto.getInfoSources(), "") ? subject.getInfoSources(): dto.getInfoSources());
        repo.save(subject);
        return new StockMarketSubjectDTO(subject);
    }

    public boolean deleteStockMarketSubject(int id){
        repo.deleteById(id);
        return true;
    }


}
