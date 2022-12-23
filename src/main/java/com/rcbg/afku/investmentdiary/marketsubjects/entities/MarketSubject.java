package com.rcbg.afku.investmentdiary.marketsubjects.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class MarketSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    String infoSources;

    @Column(nullable = false)
    boolean hasDividend;

    @OneToMany(mappedBy = "marketSubject")
    @JsonBackReference
    List<MarketOperation> marketOperations;

    @ManyToMany
    @JsonBackReference
    Set<BrokerAccount> brokerAccounts;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfoSources() {
        return infoSources;
    }

    public void setInfoSources(String infoSources) {
        this.infoSources = infoSources;
    }

    public boolean isHasDividend() {
        return hasDividend;
    }

    public void setHasDividend(boolean hasDividend) {
        this.hasDividend = hasDividend;
    }

    public List<MarketOperation> getMarketOperations() {
        return marketOperations;
    }

    public void setMarketOperations(List<MarketOperation> marketOperation) {
        this.marketOperations = marketOperation;
    }

    public void addMarketOperations(MarketOperation marketOperation){
        this.marketOperations.add(marketOperation);
    }

    public Set<BrokerAccount> getBrokerAccounts() {
        return brokerAccounts;
    }

    public void setBrokerAccounts(Set<BrokerAccount> brokerAccounts) {
        this.brokerAccounts = brokerAccounts;
    }
}
