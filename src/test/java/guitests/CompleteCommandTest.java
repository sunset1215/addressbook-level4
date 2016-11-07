package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.task.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.logic.commands.CompleteCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0138704E
public class CompleteCommandTest extends TaskBookGuiTest {

    private TestTask[] currentList = td.getTypicalTasks();
    
    @Test
    public void complete_invalidIndex() {
        commandBox.runCommand("list /a");
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    @Test
    public void complete_invalidArgs() {
        commandBox.runCommand("list /a");
        commandBox.runCommand("complete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void complete_taskAlreadyCompleted() {
        commandBox.runCommand("list /a");
        commandBox.runCommand("complete 1");
        commandBox.runCommand("list /a");
        commandBox.runCommand("complete 1");
        assertResultMessage(CompleteCommand.MESSAGE_TASK_ALREADY_COMPLETED);
    }
    
    @Test
    public void complete_nonEmptyList() {
        commandBox.runCommand("list /a");
        
        int targetIndex = 1;
        TestTask taskToComplete = currentList[targetIndex-1]; //-1 because array uses zero indexing
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);

        //complete the last in the list
        targetIndex = currentList.length;
        taskToComplete = currentList[targetIndex-1];
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);

        //complete from the middle of the list
        targetIndex = currentList.length/2;
        taskToComplete = currentList[targetIndex-1];
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertCompleteSuccess(targetIndex, taskToComplete, currentList);
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
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete.toString()));
    }
}
