package seedu.task.commons.handlers.ui;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.SelectCalendarDateEvent;

public class SelectCalendarDateHandler implements EventHandler<ActionEvent> {

    private LocalDate selectedDate;
    private DatePicker datePicker;
    
    public SelectCalendarDateHandler(DatePicker datePicker) {
        this.datePicker = datePicker;
    }
    
    @Override
    public void handle(ActionEvent event) {
        selectedDate = datePicker.getValue();
        EventsCenter.getInstance().post(new SelectCalendarDateEvent(selectedDate));
    }
}
