package com.rcbg.afku.investmentdiary.subjects.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.repositories.StockMarketSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

@Service
@Validated
public class StockMarketSubjectManagementService {

    private final StockMarketSubjectRepository repo;
    private final StockMarketSubjectBrowseService browseService;

    @Autowired
    public StockMarketSubjectManagementService(StockMarketSubjectRepository repo, StockMarketSubjectBrowseService browseService) {
        this.repo = repo;
        this.browseService = browseService;
    }

    @Validated(OnCreate.class)
    public StockMarketSubjectDTO createStockMarketSubject(@Valid StockMarketSubjectDTO dto){
        StockMarketSubject subject = StockMarketSubjectMapper.INSTANCE.toEntity(dto);
        repo.save(subject);
        return StockMarketSubjectMapper.INSTANCE.toDTO(subject);
    }

    @Validated(OnUpdate.class)
    public StockMarketSubjectDTO updateStockMarketSubjectById(int id, @Valid StockMarketSubjectDTO dto){
        StockMarketSubject subject = browseService.getStockMarketSubjectDomainObjectById(id);
        subject = StockMarketSubjectMapper.INSTANCE.updateEntity(dto, subject);
        repo.save(subject);
        return StockMarketSubjectMapper.INSTANCE.toDTO(subject);
    }

    public boolean deleteStockMarketSubject(int id){
        repo.deleteById(id);
        return true;
    }


}
