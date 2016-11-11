package seedu.task.commons.handlers.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ClickListAllButtonEvent;
import seedu.task.commons.events.ui.ClickListButtonEvent;
import seedu.task.commons.events.ui.ClickListCompleteButtonEvent;

public class ClickListButtonHandler implements EventHandler<ActionEvent> {
    
    private int option;
    
    public static final int LIST_DEFAULT = 1;
    public static final int LIST_ALL = 2;
    public static final int LIST_COMPLETE = 3;
    
    public ClickListButtonHandler(int option) {
        this.option = option;
    }
    
    @Override
    public void handle(ActionEvent event) {
        switch (option) {
        case 1:
            EventsCenter.getInstance().post(new ClickListButtonEvent());
            break;
        case 2:
            EventsCenter.getInstance().post(new ClickListAllButtonEvent());
            break;
        case 3:
            EventsCenter.getInstance().post(new ClickListCompleteButtonEvent());
            break;
        default:
            assert false : "Unknown option for list button";
        }
    }

}
