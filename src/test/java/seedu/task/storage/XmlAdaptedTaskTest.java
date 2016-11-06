package seedu.task.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;

//@@author A0138704E
public class XmlAdaptedTaskTest {
    
    private Name name;
    private TaskDate taskStartDate;
    private TaskDate taskEndDate;
    private Status status;
    
    private ReadOnlyTask source;
    
    @Before
    public void setup() throws IllegalValueException {
        name = new Name("test task");
        taskStartDate = DateUtil.convertJaxbStringToTaskDate("12 Oct 2016 15:00");
        taskEndDate = DateUtil.convertJaxbStringToTaskDate("14 Oct 2016 15:00");
        status = new Status(Status.STATUS_PENDING);
    }
    
    @Test
    public void XmlAdaptedTask_bothDatesAreNull_returnToDo() throws IllegalValueException {
        source = new Task(name, status);
        Task result = new XmlAdaptedTask(source).toModelType();
        assertEquals("Should be to do task", Task.class, result.getClass());
    }
    
    @Test
    public void XmlAdaptedTask_haveEndDate_returnDeadline() throws IllegalValueException {
        source = new DeadlineTask(name, taskEndDate);
        Task result = new XmlAdaptedTask(source).toModelType();
        assertEquals("Should be deadline task", DeadlineTask.class, result.getClass());
    }
    
    @Test
    public void XmlAdaptedTask_haveBothDate_returnEvent() throws IllegalValueException {
        source = new EventTask(name, taskStartDate, taskEndDate);
        Task result = new XmlAdaptedTask(source).toModelType();
        assertEquals("Should be event task", EventTask.class, result.getClass());
    }

}
