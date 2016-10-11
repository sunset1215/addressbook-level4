package seedu.address.model.person;

import java.util.Date;

import com.sun.glass.ui.monocle.linux.LinuxInputProcessor.Logger;

import seedu.address.commons.exceptions.IllegalValueException;

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
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskDate(Date taskDate) throws IllegalValueException {
        assert taskDate != null;
        this.taskDate = taskDate;
    }

    /**
     * Returns true if a given string is a valid person task.
     */
    public static boolean isValidDate(String test) {
    	System.out.println("this valid date does not work yet, not sure how to do regex----------------------");
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskDate.toString();
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

