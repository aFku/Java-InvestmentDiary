package com.rcbg.afku.investmentdiary.transactions.entities;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.sql.Date;

@Entity
public class StockMarketTransaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false)
    Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Column(nullable = false, updatable = false)
    Date transactionDate;

    @Column(nullable = false, updatable = false)
    BigDecimal pricePerOne;

    @Column(nullable = false, updatable = false)
    int volume;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @Embedded
    TransactionDescription description;

    @ManyToOne
    StockMarketSubject subject;

    @ManyToOne
    Account account;

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

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public TransactionDescription getDescription() {
        return description;
    }

    public void setDescription(TransactionDescription description) {
        this.description = description;
    }

    public StockMarketSubject getSubject() {
        return subject;
    }

    public void setSubject(StockMarketSubject subject) {
        this.subject = subject;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
