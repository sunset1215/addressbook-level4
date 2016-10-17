package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 * Represents a data change in the Task List Panel
 */
public class TaskPanelDataChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
