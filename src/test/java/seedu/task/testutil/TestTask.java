package seedu.task.testutil;

import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private TaskDate endDate;
    private TaskDate startDate;
    private Status status;

    public TestTask() {
        this.status = new Status(Status.STATUS_PENDING);
    }

    public void setName(Name name) {
        this.name = name;
    }

    // @@author A0153658W
    public void setStartDate(TaskDate date) {
        this.startDate = date;
    }

    public void setEndDate(TaskDate date) {
        this.endDate = date;
    }
    // @@author

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName().fullName);
        boolean hasStart = startDate != null;
        boolean hasEnd = endDate != null;

        if (hasStart && hasEnd) {
            sb.append(" start from ");
            sb.append(startDate.toString());
            sb.append(" to ");
            sb.append(endDate.toString());
        } else if (hasEnd) {
            sb.append(" due ");
            sb.append(endDate.toString());
        } else {
            // do nothing here
        }
        return sb.toString();
    }

    //@@author A0161247J
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add \"" + this.getName().fullName + "\" ");
        boolean hasStart = startDate != null;
        boolean hasEnd = endDate != null;

        if (hasStart && hasEnd) {
            sb.append(" on ");
            sb.append(startDate.toString() + " ");
            sb.append(" to ");
            sb.append(endDate.toString());
        } else if (hasStart || hasEnd) {
            sb.append(" on ");
            if (hasStart) {
                sb.append(startDate.toString());
            } else {
                sb.append(endDate.toString());
            }
        }
        return sb.toString();
    }
    //@@author

    @Override
    public TaskDate getStart() {
        return startDate;
    }

    @Override
    public TaskDate getEnd() {
        return endDate;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setComplete() {
        status.setComplete();
    }

    public boolean isComplete() {
        return status.isComplete();
    }

}
