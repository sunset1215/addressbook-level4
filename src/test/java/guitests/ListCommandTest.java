package guitests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import seedu.task.logic.commands.ListCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class ListCommandTest extends TaskBookGuiTest {

    @Test
    public void list() {

        //complete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        commandBox.runCommand("complete " + targetIndex);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);

        //complete the last in the list
        targetIndex = currentList.length;
        commandBox.runCommand("complete " + targetIndex);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);

        //complete from the middle of the list
        targetIndex = currentList.length/2;
        commandBox.runCommand("complete " + targetIndex);
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);

        //list completed tasks
        final boolean isComplete = true;
        TestTask[] completedList = TestUtil.getTasksFromListByStatus(currentList, isComplete);
        assertListSuccess(completedList, "list /c", ListCommand.MESSAGE_LIST_COMPLETE_SUCCESS);
        
        //list pending tasks
        TestTask[] pendingList = TestUtil.getTasksFromListByStatus(currentList, !isComplete);
        assertListSuccess(pendingList, "list /p", ListCommand.MESSAGE_LIST_PENDING_SUCCESS);
        
        //list all tasks
        assertListSuccess(currentList, "list /a", ListCommand.MESSAGE_LIST_ALL_SUCCESS);
        
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
