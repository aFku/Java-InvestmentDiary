package com.rcbg.afku.investmentdiary.brokeraccounts.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Entity
public class BrokerAccount {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(updatable = false, nullable = false)
    Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Column(nullable = false)
    String provider;

    @Column(nullable = false)
    String accountId;

    @JsonBackReference
    @OneToMany(mappedBy = "brokerAccount", cascade = CascadeType.ALL)
    List<MarketOperation> marketOperations;

    @ManyToMany
    @JsonBackReference
    Set<MarketSubject> ownedMarketSubjects;

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getProvider() {
        return provider;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<MarketOperation> getMarketOperations() {
        return marketOperations;
    }

    public void setMarketOperations(List<MarketOperation> marketOperations) {
        this.marketOperations = marketOperations;
    }

    public void addMarketOperation(MarketOperation marketOperation){
        this.marketOperations.add(marketOperation);
    }

    public Set<MarketSubject> getOwnedMarketSubjects() {
        return ownedMarketSubjects;
    }

    public void setOwnedMarketSubjects(Set<MarketSubject> ownedMarketSubjects) {
        this.ownedMarketSubjects = ownedMarketSubjects;
    }
}
