//@@author A0153723J
package seedu.task.commons.events.ui;

import java.time.LocalDate;

import seedu.task.commons.events.BaseEvent;

public class ListButtonEvent extends BaseEvent{
	public final LocalDate date;

	public ListButtonEvent(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
