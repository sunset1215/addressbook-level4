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
        //list all tasks as default on launch is to list tasks due today
        commandBox.runCommand("list /a");
        int targetIndex = 1;
        TestTask taskToComplete = currentList[targetIndex-1]; //-1 because array uses zero indexing
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);

        //complete the last in the list
        targetIndex = currentList.length;
        taskToComplete = currentList[targetIndex-1];
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);

        //complete from the middle of the list
        targetIndex = currentList.length/2;
        taskToComplete = currentList[targetIndex-1];
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);

        //invalid index
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        //cannot mark completed task as complete again
        commandBox.runCommand("list /a");
        commandBox.runCommand("complete 1");
        assertResultMessage(CompleteCommand.MESSAGE_TASK_ALREADY_COMPLETED);

    }
    
    /**
     * Runs the complete command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to complete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks.
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, TestTask taskToComplete, TestTask[] currentList) {
        commandBox.runCommand("complete " + targetIndexOneIndexed);
        
        //confirm the task at target list index has the same status as the task to complete and is completed
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
}
