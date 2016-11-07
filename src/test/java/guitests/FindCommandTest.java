package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find assignment", td.assignment, td.project); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find assignment",td.project);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
