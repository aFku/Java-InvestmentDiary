package com.rcbg.afku.investmentdiary.unit.marketoperations;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.entities.OperationType;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Random;

public class CommonUtilsMarketOperation {

    protected final Random random = new Random();

    protected MarketOperation createRandomOperation(){
        MarketOperation operation = new MarketOperation();
        operation.setOperationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        operation.setOperationType(OperationType.values()[random.nextInt(OperationType.values().length)]);
        operation.setPricePerOne(new BigDecimal(BigInteger.valueOf(random.nextInt(1000)), 2));
        operation.setVolume(random.nextInt(100));
        MarketSubject subject = Mockito.mock(MarketSubject.class);
        int subjectId = random.nextInt(100);
        Mockito.when(subject.getId()).thenReturn(subjectId);
        operation.setMarketSubject(subject);
        BrokerAccount account = Mockito.mock(BrokerAccount.class);
        int accountId = random.nextInt(100);
        Mockito.when(account.getId()).thenReturn(accountId);
        operation.setBrokerAccount(account);
        return operation;
    }

    protected void assertDTOWithOperationInstance(MarketOperationDTO dto1, MarketOperation op){
        Assertions.assertEquals(dto1.getId(), op.getId());
        Assertions.assertEquals(dto1.getOperationType(), op.getOperationType().name());
        Assertions.assertEquals(dto1.getPricePerOne(), op.getPricePerOne());
        Assertions.assertEquals(dto1.getVolume(), op.getVolume());
        Assertions.assertEquals(dto1.getOperationDate(), op.getOperationDate());
        Assertions.assertEquals(dto1.getDescription(), op.getDescription());
        Assertions.assertEquals(dto1.getSubjectId(), op.getMarketSubject().getId());
        Assertions.assertEquals(dto1.getAccountId(), op.getBrokerAccount().getId());
    }
}
