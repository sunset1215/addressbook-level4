package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a parent class, Task, in the Task List.
 */
public class Task implements ReadOnlyTask{
    private Name name;
    private UniqueTagList tags;

    /**
     * A task must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName());
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    /*
     * Replaces this task name with new task name
     */
    public void setName(Name newName){
    	this.name = newName;
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
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public Name getName() {
		return name;
	}

	@Override
	public UniqueTagList getTags() {
		return tags;
	}

	@Override
	public TaskDate getStart() {
		return null;
	}

	@Override
	public TaskDate getEnd() {
		return null;
	}

}
