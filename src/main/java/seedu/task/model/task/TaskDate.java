package seedu.task.model.task;

import java.time.LocalDateTime;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;

/**
 * Represents a Task date in the Task List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskDate implements Comparable<TaskDate> {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be specified in following format"
    		+ " 06-10-2016 14:00";
    public static final String DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}\\s\\d{2}:\\d{2}";

    public final LocalDateTime taskDate;

    /**
     * Validates given Date.
     */
    public TaskDate(LocalDateTime taskDate) {
        assert taskDate != null;
        this.taskDate = taskDate;
    }
    
    /**
     * Validates given Date.
     */
    public TaskDate(String strDateTime) throws IllegalValueException {
        strDateTime = strDateTime.trim();
        if (!isValidDate(strDateTime)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.taskDate = DateUtil.parseStringToLocalDateTime(strDateTime);
    }
    
    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    public LocalDateTime getTaskDate() {
        return taskDate;
    }
    
    @Override
    public String toString() {
        return DateUtil.formatLocalDateTimeToString(taskDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.taskDate.equals(((TaskDate) other).taskDate)); // state check
    }

    @Override
    public int hashCode() {
        return taskDate.hashCode();
    }

    @Override
    public int compareTo(TaskDate td1) {
        if (this.taskDate.isAfter(td1.taskDate)) {
            return 1;
        } else if (this.taskDate.isEqual(td1.taskDate)) {
            return 0;
        } else {
            return -1;
        }
    }

}

