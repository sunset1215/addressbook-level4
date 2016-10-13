package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Task;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList {

    private UniqueTaskList tasks;
    private UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskList() {}

    /**
     * Persons and Tags are copied into this task list
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this task list
     */
    public TaskList(UniqueTaskList tasks, UniqueTagList tags) {
        this.tasks = copyUniqueTaskList(tasks);
        this.tags = copyUniqueTagList(tags);
        // the line of code below is the original code
        // I used the above method to copy the lists
        // because the original code changes all the tasks that is read from
        // the storage into tasks, instead of keeping them as eventtask or
        // deadline task or task.
        //resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }

//// list overwrite operations
    
    /*
     * Returns a copy of the given unique tag list
     */
    private UniqueTagList copyUniqueTagList(UniqueTagList tags) {
        UniqueTagList newList = new UniqueTagList();
        for (int i=0; i<tags.size(); i++) {
            try {
                newList.add(tags.getTagFromIndex(i));
            } catch (DuplicateTagException e) {
                // this should not happen since we're just copying items over to a new list
            }
        }
        return newList;
    }

    /*
     * Returns a copy of the given unique task list
     */
    private UniqueTaskList copyUniqueTaskList(UniqueTaskList tasks) {
        UniqueTaskList newList = new UniqueTaskList();
        for (int i=0; i<tasks.size(); i++) {
            try {
                newList.add(tasks.getTaskFromIndex(i));
            } catch (DuplicateTaskException e) {
                // this should not happen since we're just copying items over to a new list
            }
        }
        return newList;
    }

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    
    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Tag> newTags) {
        setTasks(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskList newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a task to the task list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
    }
    
    /**
     * Adds a task to the task list at a given index.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(int taskIndex, Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(taskIndex, p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public int getIndex(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException{
    	return tasks.getIndex(key);
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.tasks.equals(((TaskList) other).tasks)
                && this.tags.equals(((TaskList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
