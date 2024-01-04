package com.vti.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GroupValidate {
    public static Boolean validateGroupName(String groupName) {
        String invalidCharacters = "!@#$%^&*()_+";

        if (groupName.length() < 6 || groupName.length() > 50 || groupName.chars().anyMatch(ch -> invalidCharacters.indexOf(ch) != -1)) {
            return false;
        }

        return true;
    }

    public static Boolean validateDate(Date date){
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);
        Date currentDate = currentCalendar.getTime();

        Calendar year2000Calendar = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        year2000Calendar.set(Calendar.HOUR_OF_DAY, 0);
        year2000Calendar.set(Calendar.MINUTE, 0);
        year2000Calendar.set(Calendar.SECOND, 0);
        year2000Calendar.set(Calendar.MILLISECOND, 0);
        Date year2000 = year2000Calendar.getTime();


        if (date == null || date.after(currentDate) || date.before(year2000)) {
            return false;
        }

        return true;
    }
}
