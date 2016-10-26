//@@author A0153723J
package seedu.task.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.DatePickedOnCalendarEvent;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private DatePicker datePicker;
    DatePickerSkin datePickerSkin;
    private static LocalDate date;
    
//    private DatePickerPopUpDemo datePicker;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a CalendarPanel.
     */
    private CalendarPanel() {

    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

    /**
     * Factory method for creating a Calendar Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the CalendarPanel must be inserted
     */
    public static CalendarPanel load(AnchorPane placeholder){
       
        CalendarPanel calendarPanel = new CalendarPanel();

        calendarPanel.datePicker = new DatePicker();
        calendarPanel.datePicker.setPrefHeight(600);
      
        DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPanel.datePicker);
        
        //event handler for when you click a specified date on the calendar
        calendarPanel.datePicker.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//gets the value of the selected date
		        date = calendarPanel.datePicker.getValue();
		        
		        //raising an event in the model manager
		        EventsCenter.getInstance().post(new DatePickedOnCalendarEvent(date));
		        System.out.println(date);
				
			}
		});
        System.out.println(date);
		Node popupContent = datePickerSkin.getPopupContent();
        
 
        placeholder.getChildren().add(popupContent);
        return calendarPanel;
    }
    
    
    
    
    
    

    
}
