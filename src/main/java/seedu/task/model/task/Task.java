//@@author A0153658W
package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;

import java.util.Objects;

/**
 * Represents a parent class, Task, in the Task List.
 */
public class Task implements ReadOnlyTask {
    private Name name;
    private Status status;

    /**
     * A task must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        status = new Status(Status.STATUS_PENDING);
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName());
        status = new Status(Status.STATUS_PENDING);
    }

    public Task(Name name, Status status) {
        assert !CollectionUtil.isAnyNull(name, status);
        this.name = name;
        this.status = status;
    }

    /*
     * Replaces this task name with new task name
     */
    public void setName(Name newName){
    	this.name = newName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public Name getName() {
		return name;
	}

	@Override
	public TaskDate getStart() {
		return null;
	}

	@Override
	public TaskDate getEnd() {
		return null;
	}

    @Override
    public Status getStatus() {
        return status;
    }
    
    /**
     * Returns true if task is already completed.
     */
    public boolean isComplete() {
        return status.isComplete();
    }
    
    /**
     * Set the task as completed.
     */
    public void setComplete() {
        status.setComplete();
    }
    
    /**
     * Set the task as pending.
     */
    public void setPending(){
    	status.setPending();
    }
}
