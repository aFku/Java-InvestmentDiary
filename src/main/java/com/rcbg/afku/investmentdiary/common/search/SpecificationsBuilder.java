package com.rcbg.afku.investmentdiary.common.search;

import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationsBuilder<T>{

    private final List<SearchCriteria> params = new ArrayList<>();

    public  SpecificationsBuilder<T> with(String key, String operation, Object value){
        SearchOperations op = SearchOperations.getNotComplexOperationBySign(operation.charAt(0));
        if (op != null){
            params.add(new SearchCriteria(key, op, value));
        }
        return this;
    }

    public Specification<T> build(){
        if(params.size() == 0){ return null;}

        Specification<T> result = new SpecificationImpl<>(params.get(0));

        for(int i = 1; i < params.size(); i++){
            result = Specification.where(result).and(new SpecificationImpl<>(params.get(i)));
        }
        return result;
    }
}
