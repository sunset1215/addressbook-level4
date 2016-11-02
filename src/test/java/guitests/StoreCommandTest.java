package guitests;

import org.junit.Test;

import seedu.task.logic.commands.StoreCommand;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.StoreCommand.MESSAGE_SUCCESS;

public class StoreCommandTest extends TaskBookGuiTest{
	
	@Test
	public void Store(){
		
		
		//check command for correct directory
		commandBox.runCommand("store");
		assertStoreSuccess("store + c:\\Users ", StoreCommand.MESSAGE_SUCCESS);
		
		//check command for an incorrect directory
		commandBox.runCommand("store + 123");
		assertResultMessage("Invlaid Directory Path");
		
		
	}

	private void assertStoreSuccess(String command, String expectedMessage) {
		commandBox.runCommand(command);
		assertResultMessage(expectedMessage);
		
	}
}
