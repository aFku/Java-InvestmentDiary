package com.rcbg.afku.investmentdiary.brokeraccounts.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Entity
public class Account {

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
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    List<StockMarketTransaction> transactions;

    @ManyToMany
    @JsonBackReference
    Set<StockMarketSubject> currentlyOwnedSubjects;

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

    public List<StockMarketTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockMarketTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(StockMarketTransaction transaction){
        this.transactions.add(transaction);
    }

    public Set<StockMarketSubject> getCurrentlyOwnedSubjects() {
        return currentlyOwnedSubjects;
    }

    public void setCurrentlyOwnedSubjects(Set<StockMarketSubject> currentlyOwnedSubjects) {
        this.currentlyOwnedSubjects = currentlyOwnedSubjects;
    }
}
