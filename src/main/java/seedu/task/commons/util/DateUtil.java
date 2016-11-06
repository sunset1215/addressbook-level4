package seedu.task.commons.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.task.model.task.TaskDate;

//@@author A0138704E
/**
 * A class for handling Dates
 */
public class DateUtil {
    
    public static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");
    public static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");
    
	/**
     * Parses a String into a LocalDateTime
     * @throws ParseException
     */
    public static LocalDateTime parseStringToLocalDateTime(String strDate) throws DateTimeParseException {
    	return LocalDateTime.parse(strDate, localDateTimeFormatter);
    }
    
    /**
     * Parses a String into a LocalDate
     * @throws ParseException
     */
    public static LocalDate parseStringToLocalDate(String strDate) throws DateTimeParseException {
    	return LocalDate.parse(strDate, localDateFormatter);
    }
    
    /**
     * Parses a String into a LocalDateTime with a specified time
     * @throws ParseException
     */
    public static LocalDateTime parseStringToLocalDateTimeWithSpecifiedTime(String strDate, String time) 
            throws DateTimeParseException {
        return parseStringToLocalDateTime(strDate + " " + time);
    }
    
    /**
     * Formats a LocalDateTime into a string
     */
    public static String formatLocalDateTimeToString(LocalDateTime date) {
        return date.format(localDateTimeFormatter);
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
    public static String convertTaskDateToJaxbString(TaskDate taskDate) {
        if (taskDate == null) {
            return "";
        }
        return taskDate.toString();
    }
    
    /**
     * Converts a given string into a TaskDate.
     */
    public static TaskDate convertJaxbStringToTaskDate(String strDate) {
        try {
            LocalDateTime date = parseStringToLocalDateTime(strDate);
            return new TaskDate(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Returns true if a taskDate falls on the same day as the given local date
     */
    public static boolean isEqual(TaskDate taskDate, LocalDate date) {
        if (taskDate == null) {
            return false;
        }
        LocalDate ldOfTaskDate = taskDate.getTaskDate().toLocalDate();
        return ldOfTaskDate.isEqual(date);
    }
    
    /**
     * Returns today's date as a LocalDate
     */
    public static LocalDate getTodayAsLocalDate() {
        LocalDate today = LocalDate.now();
        return today;
    }
    
    /**
     * Returns today's date as a LocalDateTime
     */
    public static LocalDateTime getTodayAsLocalDateTime() {
        LocalDateTime today = LocalDateTime.now();
        return today;
    }

}
