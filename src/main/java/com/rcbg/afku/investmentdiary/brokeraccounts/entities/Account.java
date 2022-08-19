package com.rcbg.afku.investmentdiary.brokeraccounts.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    int id;

    @Column(updatable = false, nullable = false)
    Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Column(nullable = false)
    String provider;

    @Column(nullable = false)
    String accountId;

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
}
