package com.rcbg.afku.investmentdiary.marketoperations.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.sql.Date;

@Entity
public class MarketOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false)
    Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Column(nullable = false, updatable = false)
    Date operationDate;

    @Column(nullable = false, updatable = false)
    BigDecimal pricePerOne;

    @Column(nullable = false, updatable = false)
    int volume;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @Embedded
    OperationDescription description;

    @ManyToOne
    @JsonManagedReference
    MarketSubject marketSubject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    BrokerAccount brokerAccount;

    public Long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
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

    public OperationDescription getDescription() {
        return description;
    }

    public void setDescription(OperationDescription description) {
        this.description = description;
    }

    public MarketSubject getMarketSubject() {
        return marketSubject;
    }

    public void setMarketSubject(MarketSubject marketSubject) {
        this.marketSubject = marketSubject;
    }

    public BrokerAccount getBrokerAccount() {
        return brokerAccount;
    }

    public void setBrokerAccount(BrokerAccount brokerAccount) {
        this.brokerAccount = brokerAccount;
    }
}
