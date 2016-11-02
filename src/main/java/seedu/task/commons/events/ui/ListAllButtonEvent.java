//@@author A0153723J
package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class ListAllButtonEvent extends BaseEvent{


	public ListAllButtonEvent() {
		
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
