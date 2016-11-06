//@@author A0153658W
package guitests;

import org.junit.Test;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.model.task.Name;
import seedu.task.model.task.TaskDate;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static seedu.task.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

import java.time.LocalDateTime;

public class EditCommandTest extends TaskBookGuiTest {

    @Test
    public void edit() throws IllegalValueException {

        // delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        // list all tasks as default on launch is to list tasks due today
        commandBox.runCommand("list /a");

        // edit floating task to deadline task
        int targetIndex = 1;
        // create new deadline task
        TestTask newTask = new TestTask();
        LocalDateTime endDate = DateUtil.parseStringToLocalDateTime("10-10-2016 15:30");
        newTask.setName(new Name(td.assignment.toString()));
        newTask.setEndDate(new TaskDate(endDate));
        assertEditDeadlineSuccess(currentList, targetIndex, newTask);
        
        // edit floating task to event task
        targetIndex = 2;
        // create new event task
        newTask = new TestTask();
        LocalDateTime startDate = DateUtil.parseStringToLocalDateTime("10-10-2016 15:30");
        endDate = DateUtil.parseStringToLocalDateTime("20-10-2016 15:30");
        newTask.setName(new Name(td.meeting.toString()));
        newTask.setStartDate(new TaskDate(startDate));
        newTask.setEndDate(new TaskDate(endDate));
        assertEditEventSuccess(currentList, targetIndex, newTask);
        
        // edit invalid task index
        commandBox.runCommand("edit " + (currentList.length + 1) + " " + newTask.getEnd());
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs edit command to edit a given task to a deadline task
     */
    private void assertEditDeadlineSuccess(TestTask[] currentList, int targetIndex, TestTask newTask) {
        TestUtil.replaceTaskFromList(currentList, newTask, targetIndex - 1);
        // run edit command
        commandBox.runCommand("edit " + targetIndex + " " + newTask.getEnd());
        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, newTask + " due " + newTask.getEnd()));
    }

    /**
     * Runs edit command to edit a given task to an event task
     */
    private void assertEditEventSuccess(TestTask[] currentList, int targetIndex, TestTask newTask) {
        TestUtil.replaceTaskFromList(currentList, newTask, targetIndex - 1);
        // run edit command
        commandBox.runCommand("edit " + targetIndex + " oct 10 3.30pm to oct 20 3.30pm");
        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS,
                newTask + " start from " + newTask.getStart() + " to " + newTask.getEnd()));
    }

}
