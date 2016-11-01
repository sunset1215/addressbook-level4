//@@author A0153658W
package seedu.task.model.task;

import java.util.Objects;

/*
 * Represents a task as an event, i.e. it will have a start and end date/time
 */
public class EventTask extends Task {
    private TaskDate startDate, endDate;

    public EventTask(Name name, TaskDate startDate, TaskDate endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventTask(Name name, TaskDate taskStartDate, TaskDate taskEndDate, Status taskStatus) {
        super(name, taskStatus);
        this.startDate = taskStartDate;
        this.endDate = taskEndDate;
    }

    public TaskDate getStartDate() {
        return startDate;
    }

    /*
     * Replaces this task's start date with the new start date
     */
    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public TaskDate getEndDate() {
        return endDate;
    }

    /*
     * Replaces this task's end date with the new end date
     */
    public void setEndDate(TaskDate newEndDate) {
        this.endDate = newEndDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName().toString());
        sb.append(" start from ");
        sb.append(startDate.toString());
        sb.append(" to ");
        sb.append(endDate.toString());
        return sb.toString();
    }

    @Override
    public TaskDate getStart() {
        return startDate;
    }

    @Override
    public TaskDate getEnd() {
        return endDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTask // instanceof handles nulls
                        && this.hashCode() == other.hashCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(getName(), "startDate:" + startDate.toString(), "endDate:" + endDate.toString());
    }
}
