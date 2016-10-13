package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.model.DeadlineTask;
import seedu.task.model.EventTask;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
    private String startDate;
	@XmlElement(required = true)
    private String endDate;

	@XmlElement
	private List<XmlAdaptedTag> tagged = new ArrayList<>();

	/**
	 * No-arg constructor for JAXB use.
	 */
	public XmlAdaptedTask() {
	}

	/**
	 * Converts a given Task into this class for JAXB use.
	 *
	 * @param source
	 *            future changes to this will not affect the created
	 *            XmlAdaptedTask
	 */
	public XmlAdaptedTask(ReadOnlyTask source) {
		name = source.getName().fullName;
		startDate = DateUtil.convertDateToJaxbString(source.getStart());
		endDate = DateUtil.convertDateToJaxbString(source.getEnd());
		tagged = new ArrayList<>();
//		for (Tag tag : source.getTags()) {
//		    tagged.add(new XmlAdaptedTag(tag));
//		}
	}

    /**
	 * Converts this jaxb-friendly adapted person object into the model's Task
	 * object.
	 *
	 * @throws IllegalValueException
	 *             if there were any data constraints violated in the adapted
	 *             person
	 */
	public Task toModelType() throws IllegalValueException {
		final List<Tag> taskTags = new ArrayList<>();
		for (XmlAdaptedTag tag : tagged) {
			taskTags.add(tag.toModelType());
		}
		final Name name = new Name(this.name);
		final TaskDate taskStartDate = DateUtil.convertJaxbStringToDate(startDate);
		final TaskDate taskEndDate = DateUtil.convertJaxbStringToDate(endDate);
		final UniqueTagList tags = new UniqueTagList(taskTags);
		return createTaskFromGivenArgs(name, taskStartDate, taskEndDate);
	}

	/**
	 * Creates a Task object based on the given parameters.
	 * Returns an EventTask if both start and end date are given.
	 * Returns a DeadlineTask if only end date is given.
	 * Returns a Task if only name is given.
	 */
	private Task createTaskFromGivenArgs(Name name, TaskDate taskStartDate, TaskDate taskEndDate) {
	    if (isEventTask(taskStartDate, taskEndDate)) {
	        return new EventTask(name, taskStartDate, taskEndDate);
	    }
	    if (isDeadline(taskEndDate)) {
	        return new DeadlineTask(name, taskEndDate);
	    }
	    return new Task(name);
	}
	
	/*
	 * Returns true if taskEndDate is not null.
	 */
    private boolean isDeadline(TaskDate taskEndDate) {
        return taskEndDate != null;
    }

    /*
     * Returns true if both taskStartDate and taskEndDate is not null.
     */
    private boolean isEventTask(TaskDate taskStartDate, TaskDate taskEndDate) {
        return taskStartDate != null && taskEndDate != null;
    }

    
}
