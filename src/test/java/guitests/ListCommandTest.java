package guitests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.ListCommand;
import seedu.task.model.task.Status;
import seedu.task.model.task.TaskDate;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0138704E
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
        //build simple list with some tasks due today
        TestTask test, exam, assignment;
        TestTask[] todayList = new TestTask[0];
        LocalDateTime today = DateUtil.getTodayAsLocalDateTime();
        
        try {
            test = new TaskBuilder().withName("test").withEndDate(new TaskDate(today)).build();
            exam = new TaskBuilder().withName("exam").withEndDate(new TaskDate(today)).build();
            assignment = new TaskBuilder().withName("assignment").withEndDate(new TaskDate(today)).build();
            todayList = TestUtil.addTasksToList(new TestTask[0], test, exam, assignment);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
        
        //setup expectations
        commandBox.runCommand("clear /a");
        commandBox.runCommand("add test " + DateUtil.formatLocalDateTimeToString(today));
        commandBox.runCommand("add exam " + DateUtil.formatLocalDateTimeToString(today));
        commandBox.runCommand("add assignment " + DateUtil.formatLocalDateTimeToString(today));
        commandBox.runCommand("list /c");
        
        //list tasks due today
        assertListSuccess(todayList, "list", ListCommand.MESSAGE_LIST_TODAY_SUCCESS);

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
