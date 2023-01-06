package com.rcbg.afku.investmentdiary.common.search;

public enum SearchOperations {
    EQUALITY,
    NEGATION,
    GREATER_THAN,
    LESS_THAN,
    LIKE;

    public static SearchOperations getNotComplexOperationBySign(char sign){
        switch (sign){
            case ':':
                return EQUALITY;
            case '!':
                return NEGATION;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '~':
                return LIKE;
            default:
                return null;
        }
    }
 }
