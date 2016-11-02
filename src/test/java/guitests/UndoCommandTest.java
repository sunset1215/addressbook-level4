//@@author A0153658W
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


import seedu.task.logic.commands.UndoCommand;
import seedu.task.model.task.Status;
import seedu.task.testutil.TestTask;

import seedu.task.testutil.TestUtil;

public class UndoCommandTest extends TaskBookGuiTest{
    @Test
    public void undo(){
        TestTask[] currentList = td.getTypicalTasks();

        //list all tasks
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
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "edit 1 " + td.assignment.toString()));
        
        // test undo for clear all
        commandBox.runCommand("clear /a");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear all"));
        
        // test undo for clearing completed tasks only
        commandBox.runCommand("complete 1");
        commandBox.runCommand("list /c");
        
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(String.format(UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, "clear"));
    }
    
    private void assertUndoCommandSuccess(String expectedMessage) {
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
