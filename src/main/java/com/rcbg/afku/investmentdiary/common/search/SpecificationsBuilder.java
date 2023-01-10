package com.rcbg.afku.investmentdiary.common.search;

import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountManagementService;
import com.rcbg.afku.investmentdiary.marketoperations.entities.OperationType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecificationsBuilder<T>{

    private final List<SearchCriteria> params = new ArrayList<>();

    public  SpecificationsBuilder<T> with(String key, String operation, String value){
        SearchOperations op = SearchOperations.getNotComplexOperationBySign(operation.charAt(0));
        if (op != null){
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                boolean boolValue = Boolean.parseBoolean(value);
                params.add(new SearchCriteria(key, op, boolValue));
            } else if (Objects.equals(key, "operationType")) {
                params.add(new SearchCriteria(key, op, OperationType.valueOf(value)));
            } else {
                try {
                    Integer intValue = Integer.parseInt(value);
                    params.add(new SearchCriteria(key, op, intValue));
                } catch (NumberFormatException e) {
                    params.add(new SearchCriteria(key, op, value));
                }
            }
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
