package guitests;

import org.junit.Test;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.model.task.Status;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0138704E
public class ClearCommandTest extends TaskBookGuiTest {

    private TestTask[] currentList = td.getTypicalTasks();
    private TestTask[] emptyList = new TestTask[0];
    
    @Test
    public void clearAll_nonEmptyList() {
        //verify a non-empty list can be cleared
        commandBox.runCommand("list /a");
        currentList = TestUtil.removeTasksFromList(currentList, td.getTypicalTasks());
        commandBox.runCommand("clear /a");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.report.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.report));
        commandBox.runCommand("delete 1");
        assertListSize(0);
    }
    
    @Test
    public void clearAll_emptyList() {
        commandBox.runCommand("clear /a");
        assertClearCommandSuccess(emptyList, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }
    
    @Test
    public void clearCompleted_nonEmptyList() {
        commandBox.runCommand("list /a");
        //complete the first 3 tasks in the list
        int targetIndex = 1;
        String completeCommand = "complete 1";
        commandBox.runCommand(completeCommand);
        commandBox.runCommand(completeCommand);
        commandBox.runCommand(completeCommand);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex + 1);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex + 2);
        currentList = TestUtil.getTasksFromListByStatus(currentList, Status.STATUS_PENDING);
        
        commandBox.runCommand("clear");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_COMPLETED_SUCCESS);
    }
    
    @Test
    public void clearCompleted_emptyList() {
        commandBox.runCommand("clear");
        assertClearCommandSuccess(emptyList, ClearCommand.MESSAGE_CLEAR_COMPLETED_FAIL);
    }

    private void assertClearCommandSuccess(TestTask[] currentList, String expectedMessage) {
        assertListSize(currentList.length);
        assertResultMessage(expectedMessage);
    }
    
}
