package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.logic.commands.CompleteCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class CompleteCommandTest extends TaskBookGuiTest {

    @Test
    public void complete() {

        //complete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertCompleteSuccess(targetIndex, currentList);

        //complete the last in the list
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertCompleteSuccess(targetIndex, currentList);

        //complete from the middle of the list
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertCompleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        //cannot mark completed task as complete again
        commandBox.runCommand("complete " + currentList.length);
        assertResultMessage(CompleteCommand.MESSAGE_TASK_ALREADY_COMPLETED);

    }
    
    /**
     * Runs the complete command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to complete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks.
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToComplete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        //confirm the completed task at target index is complete
        assertTrue(taskListPanel.getTask(targetIndexOneIndexed-1).getStatus().isComplete());

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
}
