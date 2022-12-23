package com.rcbg.afku.investmentdiary.marketsubjects.services;

import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectMapper;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.exceptions.MarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.marketsubjects.repositories.MarketSubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
public class MarketSubjectManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MarketSubjectManagementService.class);

    private final MarketSubjectRepository repo;
    private final MarketSubjectBrowseService browseService;

    @Autowired
    public MarketSubjectManagementService(MarketSubjectRepository repo, MarketSubjectBrowseService browseService) {
        this.repo = repo;
        this.browseService = browseService;
    }

    @Transactional
    @Validated(OnCreate.class)
    public MarketSubjectDTO createMarketSubject(@Valid MarketSubjectDTO dto){
        MarketSubject subject = MarketSubjectMapper.INSTANCE.toEntity(dto);
        repo.save(subject);
        MarketSubjectDTO newDto = MarketSubjectMapper.INSTANCE.toDTO(subject);
        logger.info(String.format("Created market subject " + newDto));
        return newDto;
    }

    @Transactional
    @Validated(OnUpdate.class)
    public MarketSubjectDTO updateMarketSubjectById(int id, @Valid MarketSubjectDTO dto){
        MarketSubject subject = browseService.getMarketSubjectDomainObjectById(id);
        subject = MarketSubjectMapper.INSTANCE.updateEntity(dto, subject);
        repo.save(subject);
        MarketSubjectDTO updatedDto = MarketSubjectMapper.INSTANCE.toDTO(subject);
        logger.info("Market subject with id: " + id + " updated to " + updatedDto);
        return updatedDto;
    }

    @Transactional
    public void deleteMarketSubject(int id){
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new MarketSubjectNotFound("Market subject with id: " + id + " not found");
        }
        logger.info("Market subject with id: " + id + " deleted");
    }
}
