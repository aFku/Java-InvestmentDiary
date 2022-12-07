package com.rcbg.afku.investmentdiary.transactions.datatransferobjects;

import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import com.rcbg.afku.investmentdiary.transactions.entities.TransactionDescription;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.sql.Date;


public class StockMarketTransactionDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Date creationDate;
    @Past
    @NotEmpty
    Date transactionDate;
    @NotEmpty
    @Positive
    BigDecimal pricePerOne;
    @NotEmpty
    @Positive
    int volume;
    @NotEmpty
    String operationType;
    TransactionDescription description;
    @NotEmpty
    @PositiveOrZero
    int subjectId;
    @NotEmpty
    @PositiveOrZero
    int accountId;

    public Long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getPricePerOne() {
        return pricePerOne;
    }

    public void setPricePerOne(BigDecimal pricePerOne) {
        this.pricePerOne = pricePerOne;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public TransactionDescription getDescription() {
        return description;
    }

    public void setDescription(TransactionDescription description) {
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public StockMarketTransactionDTO(){}

    public StockMarketTransactionDTO(StockMarketTransaction transaction){
        this.id = transaction.getId();
        this.creationDate = transaction.getCreationDate();
        this.transactionDate = transaction.getTransactionDate();
        this.pricePerOne = transaction.getPricePerOne();
        this.volume = transaction.getVolume();
        this.operationType = transaction.getOperationType().name();
        this.description = transaction.getDescription();
        this.subjectId = transaction.getSubject().getId();
        this.accountId = transaction.getAccount().getId();
    }
}
