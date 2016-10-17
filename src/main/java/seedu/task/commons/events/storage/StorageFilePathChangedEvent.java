package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

public class StorageFilePathChangedEvent extends BaseEvent{
	
	public String filePath;
	
	public StorageFilePathChangedEvent(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
