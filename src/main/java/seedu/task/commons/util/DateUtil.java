package seedu.task.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import seedu.task.model.task.TaskDate;

/**
 * A class for handling Dates
 * @author Vivian
 *
 */
public class DateUtil {
    
    private static DateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
    private static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	/**
     * Parses a String into a Date
     * @throws ParseException
     */
    public static Date parseStringToDate(String strDate) throws ParseException {
        return dateFormatter.parse(strDate);
    }
    
    /**
     * Formats a Date into a string
     */
    public static String formatDateToString(Date date) {
        return dateFormatter.format(date);
    }
    
    /**
     * Formats a LocalDate into a string
     */
    public static String formatLocalDateToString(LocalDate date) {
        return date.format(localDateFormatter);
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
    
    /**
     * Returns true if both dates fall on the same day.
     */
    public static boolean isEqual(TaskDate taskDate, LocalDate date) {
        if (taskDate == null) {
            return false;
        }
        String date1 = taskDate.toString();
        String date2 = date.format(localDateFormatter);
        return date1.equals(date2);
    }
}
