package com.eightbit.fott.util;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by antran on 12/4/17.
 */

public class DateTimeHelper {

    private static final SimpleDateFormat srcDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String getFormattedDateFrom(String date) {
        try {
            Date myDate = srcDateFormat.parse(date);
            Calendar srcTime = Calendar.getInstance();
            srcTime.setTimeInMillis(myDate.getTime());

            Calendar now = Calendar.getInstance();
            final String timeFormatString = ", HH:mm";
            final String dateTimeFormatString = "d MMMM, HH:mm";
            if (now.get(Calendar.DATE) == srcTime.get(Calendar.DATE) ) {
                return "Today " + DateFormat.format(timeFormatString, srcTime);
            } else {
                return DateFormat.format(dateTimeFormatString, srcTime).toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }



    }
}
