package com.rcbg.afku.investmentdiary.subjects.services;

import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.subjects.repositories.StockMarketSubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class StockMarketSubjectManagementService {

    private static final Logger logger = LoggerFactory.getLogger(StockMarketSubjectManagementService.class);

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
        StockMarketSubjectDTO newDto = StockMarketSubjectMapper.INSTANCE.toDTO(subject);
        logger.info(String.format("Created subject " + newDto));
        return newDto;
    }

    @Validated(OnUpdate.class)
    public StockMarketSubjectDTO updateStockMarketSubjectById(int id, @Valid StockMarketSubjectDTO dto){
        StockMarketSubject subject = browseService.getStockMarketSubjectDomainObjectById(id);
        subject = StockMarketSubjectMapper.INSTANCE.updateEntity(dto, subject);
        repo.save(subject);
        StockMarketSubjectDTO updatedDto = StockMarketSubjectMapper.INSTANCE.toDTO(subject);
        logger.info("Subject with id: " + id + " updated to " + updatedDto);
        return updatedDto;
    }

    public boolean deleteStockMarketSubject(int id){
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new StockMarketSubjectNotFound("Subject with id: " + id + " not found");
        }
        logger.info("Subject with id: " + id + " deleted");
        return true;
    }
}
