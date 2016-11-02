//@@author A0153658W
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.UndoCommand;
import seedu.task.model.task.Status;
import seedu.task.testutil.TestTask;

import seedu.task.testutil.TestUtil;

public class UndoCommandTest extends TaskBookGuiTest {
    @Test
    public void undo() {
        TestTask[] currentList = td.getTypicalTasks();

        // list all tasks
        commandBox.runCommand("list /a");

        // test undo for adding a task
        commandBox.runCommand("add do laundry");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "add do laundry"));

        // test undo for deleting a task
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "delete 1"));

        // test undo for editing a floating task to deadline task
        commandBox.runCommand("edit 1 10-10-2016");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "edit 1 10-10-2016 17:30"));

        // test undo for editing a floating task to event task
        commandBox.runCommand("edit 1 10-10-2016 20-10-2016");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "edit 1 10-10-2016 17:30 20-10-2016 17:30"));
        
        // test undo for complete
        commandBox.runCommand("complete 1");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "complete 1"));
        
        // test undo for clear all
        commandBox.runCommand("clear /a");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear /a"));

        // test undo for clearing completed tasks only
        commandBox.runCommand("complete 1");
        commandBox.runCommand("list /c");

        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear"));
        commandBox.runCommand("list /c");
        commandBox.runCommand("list /a");
        
        // test an invalid undo, when it's reached the top of the undo stack
        commandBox.runCommand("undo"); //undo "clear"
        commandBox.runCommand("undo"); //undo "complete 1"
        assertResultMessage("Nothing to undo.");
    }

    private void assertUndoCommandSuccess(String expectedMessage) {
        assertResultMessage(expectedMessage);
    }
}
