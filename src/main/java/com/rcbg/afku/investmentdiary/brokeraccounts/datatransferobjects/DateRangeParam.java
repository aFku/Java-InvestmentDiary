package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class DateRangeParam {

    @PastOrPresent(message = "Start date must be from past")
    Date startDate;
    @PastOrPresent(message = "Stop date must be from past")
    Date stopDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }
}
