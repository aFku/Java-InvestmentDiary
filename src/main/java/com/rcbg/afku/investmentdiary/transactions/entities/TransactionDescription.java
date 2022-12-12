package com.rcbg.afku.investmentdiary.transactions.entities;

import javax.persistence.Embeddable;

@Embeddable
public class TransactionDescription {

    String advantages;
    String disadvantages;
    String reason;

    public String getAdvantages() {
        return advantages;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
