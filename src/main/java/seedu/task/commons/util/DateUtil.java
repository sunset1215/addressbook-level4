package seedu.task.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.task.model.task.TaskDate;

public class DateUtil {
	
	/**
     * Parses a String into a Date
     * 
     * @throws ParseException
     */
    public static Date parseStringToDate(String strDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        Date date = df.parse(strDate);
        return date;
    }
    
    /**
     * Converts a given TaskDate into a string.
     */
    public static String convertDateToJaxbString(TaskDate taskDate) {
        if (taskDate == null) {
            return "";
        }
        return taskDate.toString();
    }
    
    /**
     * Converts a given string into a TaskDate.
     */
    public static TaskDate convertJaxbStringToDate(String strDate) {
        try {
            Date date = DateUtil.parseStringToDate(strDate);
            return new TaskDate(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
