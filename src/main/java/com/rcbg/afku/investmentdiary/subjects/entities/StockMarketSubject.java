package com.rcbg.afku.investmentdiary.subjects.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class StockMarketSubject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    String infoSources;

    boolean hasDividend;

    @OneToMany(mappedBy = "subject")
    @JsonBackReference
    List<StockMarketTransaction> transactions;

    @ManyToMany
    @JsonBackReference
    Set<Account> holdingAccounts;

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

    public boolean hasDividend() {
        return hasDividend;
    }

    public void setHasDividend(boolean hasDividend) {
        this.hasDividend = hasDividend;
    }

    public List<StockMarketTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockMarketTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(StockMarketTransaction transaction){
        this.transactions.add(transaction);
    }

    public Set<Account> getHoldingAccounts() {
        return holdingAccounts;
    }

    public void setHoldingAccounts(Set<Account> holdingAccounts) {
        this.holdingAccounts = holdingAccounts;
    }
}
