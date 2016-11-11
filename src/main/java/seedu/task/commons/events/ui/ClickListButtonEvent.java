//@@author A0153723J
package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class ClickListButtonEvent extends BaseEvent{

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
