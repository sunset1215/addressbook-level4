package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.task.*;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask assignment, meeting, test, exam, project, movie, discussion, report, powerpoint;

    public TypicalTestTasks() {
        try {
            assignment =  new TaskBuilder()
                    .withName("assignment")
                    .build();
            meeting = new TaskBuilder()
                    .withName("meeting with John")
                    .build();
            test = new TaskBuilder()
                    .withName("driving test")
                    .build();
            exam = new TaskBuilder()
                    .withName("programming exam")
                    .build();
            project = new TaskBuilder()
                    .withName("software engineering project assignment")
                    .build();
            movie = new TaskBuilder()
                    .withName("watch The Accountant")
                    .withEndDate(new TaskDate("2 Aug 2015 14:00"))
                    .build();
            discussion = new TaskBuilder()
                    .withName("group discussion")
                    .withDates(new TaskDate("2 Feb 2020 14:00"), new TaskDate("25 Dec 2023 14:00"))
                    .build();

            //Manually added
            report = new TaskBuilder()
                    .withName("write report")
                    .build();
            powerpoint = new TaskBuilder()
                    .withName("create powerpoint for project")
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(assignment));
            ab.addTask(new Task(meeting));
            ab.addTask(new Task(test));
            ab.addTask(new Task(exam));
            ab.addTask(new Task(project));
            ab.addTask(new DeadlineTask(movie.getName(), movie.getEnd()));
            ab.addTask(new EventTask(discussion.getName(), discussion.getStart(), discussion.getEnd()));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { assignment, meeting, test, exam, project, movie, discussion };
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
