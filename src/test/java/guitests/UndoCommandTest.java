//@@author A0153658W
package guitests;

import org.junit.Test;

import seedu.task.logic.commands.UndoCommand;

public class UndoCommandTest extends TaskBookGuiTest {
    @Test
    public void undo() {
        // list all tasks
        commandBox.runCommand("list /a");

        // test undo for adding a task
        commandBox.runCommand("add \"do laundry\"");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "add do laundry"));

        // test undo for deleting a task
        commandBox.runCommand("delete 1");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "delete 1"));

        // test undo for editing a floating task to deadline task
        commandBox.runCommand("edit 1 10 oct 5.30pm");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "edit 1 10 Oct 2016 17:30"));

        // test undo for editing a floating task to event task
        commandBox.runCommand("edit 1 10 oct 2016 5.30pm to 20 oct 2016 5.30pm");
        assertUndoCommandSuccess(
                String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "edit 1 10 Oct 2016 17:30 20 Oct 2016 17:30"));

        // test undo for complete
        commandBox.runCommand("complete 1");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "complete 1"));

        // test undo for clear all
        commandBox.runCommand("clear /a");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear /a"));

        // test undo for clearing completed tasks only
        commandBox.runCommand("complete 1");
        commandBox.runCommand("list /c");

        commandBox.runCommand("clear");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear"));
        commandBox.runCommand("list /c");
        commandBox.runCommand("list /a");

        // test an invalid undo, when it's reached the top of the undo stack
        commandBox.runCommand("undo"); // undo "clear"
        commandBox.runCommand("undo"); // undo "complete 1"
        assertResultMessage("Nothing to undo.");
    }

    /**
     * Runs the undo command to undo previous command and confirms that the
     * result is correct.
     */
    private void assertUndoCommandSuccess(String expectedMessage) {
        commandBox.runCommand("undo");
        assertResultMessage(expectedMessage);
    }
}
