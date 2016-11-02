//@@author A0153723J
package seedu.task.commons.events.ui;

import seedu.task.model.task.Status;
import seedu.task.commons.events.BaseEvent;

public class ListPendingButtonEvent extends BaseEvent{
	
	

	public ListPendingButtonEvent(boolean status) {
		status = Status.STATUS_PENDING;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
