package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Status status;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
        this.status = new Status(Status.STATUS_PENDING);
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public TaskDate getStart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TaskDate getEnd() {
        // TODO Auto-generated method stub
        return null;
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
