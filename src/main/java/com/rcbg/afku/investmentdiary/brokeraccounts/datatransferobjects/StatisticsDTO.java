package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import java.math.BigDecimal;

public class StatisticsDTO {
    DateRangeParam dateRange;
    int volumesSold;
    int volumesBought;
    BigDecimal moneySpent;
    BigDecimal moneyEarned;
    int uniqueSubjectsOperated;

    public DateRangeParam getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRangeParam dateRange) {
        this.dateRange = dateRange;
    }

    public int getVolumesSold() {
        return volumesSold;
    }

    public void setVolumesSold(int volumesSold) {
        this.volumesSold = volumesSold;
    }

    public int getVolumesBought() {
        return volumesBought;
    }

    public void setVolumesBought(int volumesBought) {
        this.volumesBought = volumesBought;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(BigDecimal moneySpent) {
        this.moneySpent = moneySpent;
    }

    public BigDecimal getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(BigDecimal moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public int getUniqueSubjectsOperated() {
        return uniqueSubjectsOperated;
    }

    public void setUniqueSubjectsOperated(int uniqueSubjectsOperated) {
        this.uniqueSubjectsOperated = uniqueSubjectsOperated;
    }
}
