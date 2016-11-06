package seedu.task.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.TaskDate;

//@@author A0138704E
public class DateUtilTest {
    
    private final String DEFAULT_TIME = "17:30";
    
    private String validDateTimeString;
    private String validDateString;
    private String invalidDateString;
    private TaskDate validTaskDate;
    private TaskDate nullTaskDate;
    
    
    @Before
    public void setup() throws IllegalValueException {
        validDateString = "23 Oct 2016";
        invalidDateString = "46 Oct 2016";
        validDateTimeString = "23 Oct 2016 " + DEFAULT_TIME;
        validTaskDate = new TaskDate(validDateTimeString);
        nullTaskDate = null;
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void parseStringToLocalDateTimeWithSpecifiedTime_validArgs_noDateTimeParseExceptionThrown() {
        DateUtil.parseStringToLocalDateTimeWithSpecifiedTime(validDateString, DEFAULT_TIME);
    }
    
    @Test
    public void parseStringToLocalDateTimeWithSpecifiedTime_invalidArgs_throwDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        DateUtil.parseStringToLocalDateTimeWithSpecifiedTime(invalidDateString, DEFAULT_TIME);
    }
    
    @Test
    public void parseStringToLocalDate_validArgs_noDateTimeParseExceptionThrown() {
        DateUtil.parseStringToLocalDate(validDateString);
    }
    
    @Test
    public void parseStringToLocalDate_invalidArgs_throwDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        DateUtil.parseStringToLocalDate(invalidDateString);
    }
    
    @Test
    public void convertTaskDateToJaxbString_nullArgs_returnEmptyString() {
        assertEquals("Empty task date should return empty string", "",
                DateUtil.convertTaskDateToJaxbString(nullTaskDate));
    }
    
    @Test
    public void convertTaskDateToJaxbString_validArgs_returnTaskDateString() {
        assertEquals("Non-empty task date should return a formatted string",
                DateUtil.formatLocalDateTimeToString(validTaskDate.getTaskDate()),
                DateUtil.convertTaskDateToJaxbString(validTaskDate));
    }
    
    @Test
    public void convertJaxbStringToTaskDate_emptyString_returnNull() {
        assertEquals("Empty string should return a null task date", nullTaskDate,
                DateUtil.convertJaxbStringToTaskDate(""));
    }
    
    @Test
    public void convertJaxbStringToTaskDate_validArgs_returnTaskDate() {
        assertEquals("Valid args string should return a valid task date", validTaskDate,
                DateUtil.convertJaxbStringToTaskDate(validDateTimeString));
    }
    
    @Test
    public void isEqual_argsHaveSameDate_returnTrue() {
        LocalDate validLocalDate = LocalDate.parse(validDateString, DateUtil.localDateFormatter);
        assertTrue("Both dates should be equal", DateUtil.isEqual(validTaskDate, validLocalDate));
    }
    
    @Test
    public void isEqual_argsDoNotHaveSameDate_returnFalse() {
        assertFalse("Both dates should not be equal", DateUtil.isEqual(validTaskDate, DateUtil.getTodayAsLocalDate()));
    }
    
    @Test
    public void isEqual_taskDateIsNull_returnFalse() {
        assertFalse("Both dates should not be equal", DateUtil.isEqual(nullTaskDate, DateUtil.getTodayAsLocalDate()));
    }
    
}
