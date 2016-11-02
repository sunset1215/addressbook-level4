//@@author A0153723J
package seedu.task.commons.events.ui;

import seedu.task.model.task.Status;
import seedu.task.commons.events.BaseEvent;
import seedu.task.model.task.Status;

public class ListCompleteButtonEvent extends BaseEvent{


	public ListCompleteButtonEvent(boolean status) {
		status = Status.STATUS_COMPLETE;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}
