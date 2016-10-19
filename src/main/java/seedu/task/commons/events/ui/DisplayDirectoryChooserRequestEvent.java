package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class DisplayDirectoryChooserRequestEvent extends BaseEvent {
	
	public static class SelectedFilePathEmptyException extends Exception {}
	
	private String chosenFilePath;
	
	public void setChosenFilePath(String selectedFilePath) {
		chosenFilePath = selectedFilePath;
	}
	
	public String getChosenFilePath() {
		return chosenFilePath;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
