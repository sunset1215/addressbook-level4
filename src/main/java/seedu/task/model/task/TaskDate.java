//@@author A0153658W
package seedu.task.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;

/**
 * Represents a Task date in the Task List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskDate implements Comparable<TaskDate> {

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
    public TaskDate(String strDateTime) throws IllegalValueException, DateTimeParseException {
        strDateTime = strDateTime.trim();
        this.taskDate = DateUtil.parseStringToLocalDateTime(strDateTime);
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

