package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Name;
import seedu.task.model.task.TaskDate;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withEndDate(TaskDate date) {
        this.task.setEndDate(date);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
