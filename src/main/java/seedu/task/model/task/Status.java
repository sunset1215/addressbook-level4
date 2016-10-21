package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task status in the Task List.
 */
public class Status {

    public static final boolean STATUS_COMPLETE = true;
    public static final boolean STATUS_PENDING = false;
    
    public static final String STATUS_COMPLETE_STRING = "Complete";
    public static final String STATUS_PENDING_STRING = "Pending";
    
    public static final String MESSAGE_STATUS_CONSTRAINTS = "Task status should be 'Complete' or 'Pending' only";
    
    private boolean status;
    
    public Status(boolean status) {
        this.status = status;
    }
    
    public Status(String status) throws IllegalValueException {
        if (status.equals(STATUS_COMPLETE_STRING)) {
            this.status = STATUS_COMPLETE;
        } else if (status.equals(STATUS_PENDING_STRING)) {
            this.status = STATUS_PENDING;
        } else {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
    }
    
    public void setComplete() {
        status = STATUS_COMPLETE;
    }
    
    public void setPending() {
        status = STATUS_PENDING;
    }
    
    @Override
    public String toString() {
        if (status == STATUS_COMPLETE) {
            return STATUS_COMPLETE_STRING;
        }
        return STATUS_PENDING_STRING;
    }
    
    public boolean isComplete() {
        return status;
    }
}
