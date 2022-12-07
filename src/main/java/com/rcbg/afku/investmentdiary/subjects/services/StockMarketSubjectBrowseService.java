package com.rcbg.afku.investmentdiary.subjects.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.PaginationAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.PaginationStockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import com.rcbg.afku.investmentdiary.subjects.repositories.StockMarketSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return new StockMarketSubjectDTO(getStockMarketSubjectDomainObjectById(id));
    }

    public PaginationStockMarketSubjectDTO getAllStockMarketSubjects(Pageable pageable){
        Page<StockMarketSubject> subjects = repo.findAll(pageable);
        return createPaginationResponse(subjects);
    }

    private List<StockMarketSubjectDTO> convertListOfAccountsToListOfDTOS(List<StockMarketSubject> subjects){
        return subjects.stream().map(StockMarketSubjectDTO::new).collect( Collectors.toList());
    }

    private PaginationStockMarketSubjectDTO createPaginationResponse(Page<StockMarketSubject> subjects){
        int page = subjects.getNumber();
        if( page >= subjects.getTotalPages()){
            throw new IllegalArgumentException("There are no page with index " + page + " for this resource. Last index is " + (subjects.getTotalPages() - 1));
        }
        ArrayList<StockMarketSubjectDTO> data = (ArrayList<StockMarketSubjectDTO>) convertListOfAccountsToListOfDTOS(subjects.getContent());
        return new PaginationStockMarketSubjectDTO(page, subjects.getTotalPages(), subjects.getSize(),
                subjects.getTotalElements(), subjects.isLast(), data);
    }

}
