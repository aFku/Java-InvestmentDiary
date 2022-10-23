package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

import com.rcbg.afku.investmentdiary.common.exceptions.BaseInvestmentDiaryRuntimeException;

public class BrokerAccountsBaseRuntimeException extends BaseInvestmentDiaryRuntimeException {

    public BrokerAccountsBaseRuntimeException(String message, int code){
        super(message, code);
    }

}
