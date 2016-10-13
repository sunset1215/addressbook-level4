package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.task.*;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask assignment, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            assignment =  new TaskBuilder().withName("assignment").build();
//            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
//                    .withEmail("johnd@gmail.com").withPhone("98765432")
//                    .withTags("owesMoney", "friends").build();
//            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
//            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
//            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
//            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
//            george = new TaskBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withAddress("4th street").build();
//
//            //Manually added
//            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
//            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(assignment));
//            ab.addTask(new Task(benson));
//            ab.addTask(new Task(carl));
//            ab.addTask(new Task(daniel));
//            ab.addTask(new Task(elle));
//            ab.addTask(new Task(fiona));
//            ab.addTask(new Task(george));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{assignment, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
