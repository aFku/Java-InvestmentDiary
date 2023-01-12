package com.rcbg.afku.investmentdiary.common.search;

import com.rcbg.afku.investmentdiary.common.exceptions.DateParseException;
import com.rcbg.afku.investmentdiary.common.utils.DateParser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.Objects;

public class SpecificationImpl<T> implements Specification<T> {

    private final SearchCriteria criteria;

    public SpecificationImpl(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria.getKey().contains("Date")){
            return datePredicate(root, query, criteriaBuilder);
        } else {
            return regularPredicate(root, query, criteriaBuilder);
        }
    }

    private Predicate regularPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        switch (criteria.getOperation()){
            case EQUALITY:
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            default:
                return null;
        }
    }

    private Predicate datePredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        try {
            switch (criteria.getOperation()) {
                case EQUALITY:
                    return criteriaBuilder.equal(root.get(criteria.getKey()), DateParser.toDate(criteria.getValue().toString()));
                case NEGATION:
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), DateParser.toDate(criteria.getValue().toString()));
                case GREATER_THAN:
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), DateParser.toDate(criteria.getValue().toString()));
                case LESS_THAN:
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), DateParser.toDate(criteria.getValue().toString()));
                case LIKE:
                    return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
                default:
                    return null;
            }
        }catch (ParseException e){
            throw new DateParseException("Cannot parse date: " + criteria.getValue().toString() + " to format: " + DateParser.getDatePattern() + " for key: " + criteria.getKey());
        }
    }


}
