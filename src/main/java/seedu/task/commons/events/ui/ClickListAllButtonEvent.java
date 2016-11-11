//@@author A0153723J
package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class ClickListAllButtonEvent extends BaseEvent{


	public ClickListAllButtonEvent() {
		
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
