package guitests;

import org.junit.Test;

import seedu.task.logic.commands.StoreCommand;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class StoreCommandTest extends TaskBookGuiTest{
	
	@Test
	public void store_invalidPath() {
		commandBox.runCommand("store + 123");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StoreCommand.MESSAGE_USAGE));
	}
	
	@Test
	public void store_validPath() {
	    
	}

	private void assertStoreSuccess(String command, String expectedMessage) {
		commandBox.runCommand(command);
		assertResultMessage(expectedMessage);
	}
}
