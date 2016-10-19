package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class DisplayDirectoryChooserRequestEvent extends BaseEvent {
	
	public static class DirectoryChooserOperationCancelledException extends Exception {}
	
	private String selectedFilePath;
	
	public void setSelectedFilePath(String selectedFilePath) {
		this.selectedFilePath = selectedFilePath;
	}
	
	public String getSelectedFilePath() {
		return selectedFilePath;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
