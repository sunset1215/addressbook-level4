package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskBook;

public class StorageFilePathChangedEvent extends BaseEvent{
	
	public String filePath;
	public ReadOnlyTaskBook taskBook;
	
	public StorageFilePathChangedEvent(String filePath, ReadOnlyTaskBook taskBook) {
		this.filePath = filePath;
		this.taskBook = taskBook;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
