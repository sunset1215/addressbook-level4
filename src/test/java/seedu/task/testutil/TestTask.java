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
    
    public void setEndDate(TaskDate date) {
        this.endDate = date;
    }
    
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
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
