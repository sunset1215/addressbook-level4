//@@author A0153723J
package seedu.task.commons.events.ui;

import java.time.LocalDate;

import seedu.task.commons.events.BaseEvent;

/**
 * 
 * @author WSH
 *Event raised when you click a date on the calendar
 */
public class DatePickedOnCalendarEvent extends BaseEvent {
	
	public final LocalDate date;
	
	public DatePickedOnCalendarEvent(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
