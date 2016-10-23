package guitests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import seedu.task.logic.commands.ListCommand;
import seedu.task.model.task.Status;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class ListCommandTest extends TaskBookGuiTest {

    @Test
    public void list() {
        
        TestTask[] currentList = td.getTypicalTasks();

        //list all tasks
        assertListSuccess(currentList, "list /a", ListCommand.MESSAGE_LIST_ALL_SUCCESS);

        //complete the first 3 tasks in the list
        int targetIndex = 1;
        String completeCommand = "complete 1";
        commandBox.runCommand(completeCommand);
        commandBox.runCommand(completeCommand);
        commandBox.runCommand(completeCommand);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex+1);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex+2);

        //list completed tasks
        TestTask[] completedList = TestUtil.getTasksFromListByStatus(currentList, Status.STATUS_COMPLETE);
        assertListSuccess(completedList, "list /c", ListCommand.MESSAGE_LIST_COMPLETE_SUCCESS);
        
        //list pending tasks
        TestTask[] pendingList = TestUtil.getTasksFromListByStatus(currentList, Status.STATUS_PENDING);
        assertListSuccess(pendingList, "list /p", ListCommand.MESSAGE_LIST_PENDING_SUCCESS);
        
        //TODO: list tasks due today

    }
    
    /**
     * Runs the list command to display tasks and confirms confirms the result is correct.
     * @param currentList A copy of the current list of tasks.
     */
    private void assertListSuccess(final TestTask[] currentList, String command, String expectedMessage) {
        commandBox.runCommand(command);
        
        //confirm the completed task at target index is complete
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the result message is correct
        assertResultMessage(expectedMessage);
    }
    
}
