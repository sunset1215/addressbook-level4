package guitests;

import org.junit.Test;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTask[] currentList = td.getTypicalTasks();
        currentList = TestUtil.removeTasksFromList(currentList, td.getTypicalTasks());
        commandBox.runCommand("clear /a");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.report.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.report));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        commandBox.runCommand("clear /a");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);

        //get a list with some completed tasks
        repopulateTaskBookWithInitialData();
        currentList = td.getTypicalTasks();
        int targetIndex = 1;
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("complete " + targetIndex);
        targetIndex = currentList.length;
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("complete " + targetIndex);
        targetIndex = currentList.length/2;
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("complete " + targetIndex);
        currentList = TestUtil.clearCompletedTasksFromList(currentList);
        
        //verify clear command can clear completed tasks
        commandBox.runCommand("clear");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_COMPLETED_SUCCESS);

        //verify clear command cannot clear when there are no completed tasks
        commandBox.runCommand("clear");
        assertClearCommandSuccess(currentList, ClearCommand.MESSAGE_CLEAR_COMPLETED_FAIL);
        
    }

    private void assertClearCommandSuccess(TestTask[] currentList, String expectedMessage) {
        assertListSize(currentList.length);
        assertResultMessage(expectedMessage);
    }
    
    private void repopulateTaskBookWithInitialData() {
        commandBox.runCommand(td.assignment.getAddCommand());
        commandBox.runCommand(td.meeting.getAddCommand());
        commandBox.runCommand(td.test.getAddCommand());
        commandBox.runCommand(td.exam.getAddCommand());
        commandBox.runCommand(td.project.getAddCommand());
        commandBox.runCommand(td.movie.getAddCommand());
        commandBox.runCommand(td.discussion.getAddCommand());
    }
}
