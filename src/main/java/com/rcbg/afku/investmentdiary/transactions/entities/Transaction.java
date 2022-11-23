package com.rcbg.afku.investmentdiary.transactions.entities;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    int id;

    @Column(updatable = false, nullable = false)
    Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Column(updatable = false, nullable = false)
    String company;

    @Column(updatable = false, nullable = false)
    Date date;

    @Column(updatable = false, nullable = false)
    BigDecimal price;

    @Column(updatable = false, nullable = false)
    int volume;

    @Column(updatable = false, nullable = false)
    BigDecimal commission;

    @Column(updatable = false, nullable = false)
    String advantages;

    @Column(updatable = false, nullable = false)
    String disadvantages;

    @Column(updatable = false, nullable = false)
    String reason;

    @ManyToOne
    Account account;

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCompany() {
        return company;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public String getAdvantages() {
        return advantages;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public String getReason() {
        return reason;
    }

    public Account getAccount() {
        return account;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
