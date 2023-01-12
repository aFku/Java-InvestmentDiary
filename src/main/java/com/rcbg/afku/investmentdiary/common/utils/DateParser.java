package com.rcbg.afku.investmentdiary.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static final String datePattern = "yyy-MM-dd";
    private static final DateFormat df = new SimpleDateFormat(datePattern);

    public static String toString(Date date){
        return df.format(date);
    }

    public static Date toDate(String date) throws ParseException {
        return df.parse(date);
    }

    public static String getDatePattern(){
        return datePattern;
    }
}
