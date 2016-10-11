package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a parent class, Task, in the Task List.
 */
public class Task implements ReadOnlyTask{
    private Name taskName;
    private UniqueTagList tags;

    /**
     * A task must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.taskName = name;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName());
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    /*
     * Replaces this task name with new task name
     */
    public void setName(Name newTaskName){
    	this.taskName = newTaskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public Name getName() {
		return taskName;
	}

	@Override
	public UniqueTagList getTags() {
		// TODO Auto-generated method stub
		return null;
	}

}
