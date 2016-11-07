package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskBook;

//@@author A0138704E
/**
 * Represents a storage file path change event
 */
public class StorageFilePathChangedEvent extends BaseEvent {
	
	private final String newFilePath;
	private final ReadOnlyTaskBook currentTaskBook;
	
	public StorageFilePathChangedEvent(String newFilePath, ReadOnlyTaskBook currentTaskBook) {
		this.newFilePath = newFilePath;
		this.currentTaskBook = currentTaskBook;
	}
	
	public String getNewFilePath() {
	    return newFilePath;
	}
	
	public ReadOnlyTaskBook getCurrentTaskBook() {
	    return currentTaskBook;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
