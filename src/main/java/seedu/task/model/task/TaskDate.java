package seedu.task.model.task;

import java.util.Date;

import seedu.task.commons.util.DateUtil;

/**
 * Represents a Task name in the Task List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskDate {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task date should be specified in following format"
    		+ "6-10-2016 14:00";
    public static final String DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4} ";

    public final Date taskDate;

    /**
     * Validates given Date.
     */
    public TaskDate(Date taskDate) {
        assert taskDate != null;
        this.taskDate = taskDate;
    }
    
    @Override
    public String toString() {
        return DateUtil.formatDateToString(taskDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.taskDate.equals(((TaskDate) other).taskDate)); // state check
    }

    @Override
    public int hashCode() {
        return taskDate.hashCode();
    }

}

