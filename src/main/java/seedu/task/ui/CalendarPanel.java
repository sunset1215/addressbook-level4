//@@author A0153723J
package seedu.task.ui;

import javafx.event.ActionEvent;
import seedu.task.model.task.Status;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.DatePickedOnCalendarEvent;
import seedu.task.commons.events.ui.ListAllButtonEvent;
import seedu.task.commons.events.ui.ListButtonEvent;
import seedu.task.commons.events.ui.ListCompleteButtonEvent;
import seedu.task.commons.events.ui.ListPendingButtonEvent;
import seedu.task.commons.util.DateUtil;

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
        VBox outerBox = new VBox();
        HBox innerBoxTop = new HBox();
        HBox innerBoxBottom = new HBox();
        Button list = new Button();
        Button listAll = new Button();
        Button listComplete = new Button();
        Button listPending = new Button();
        
        

        calendarPanel.datePicker = new DatePicker();
        calendarPanel.datePicker.setPrefHeight(600);
        calendarPanel.datePicker.setStyle("-fx-font-size: 2em;");

        list.setText("List"); listAll.setText("List All"); 
        listComplete.setText("List Complete"); listPending.setText("List Pending");
        
        list.setPrefWidth(175); listAll.setPrefWidth(175);
        listComplete.setPrefWidth(175); listPending.setPrefWidth(175);
        
        list.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//gets the value of the selected date
		        date = DateUtil.getTodayAsLocalDate();
		        
		        //raising an event in the model manager
		        EventsCenter.getInstance().post(new ListButtonEvent(date));
				
			}
		});
        
        listAll.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventsCenter.getInstance().post(new ListAllButtonEvent());
				
			}
		});
        
        listPending.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventsCenter.getInstance().post(new ListPendingButtonEvent(Status.STATUS_PENDING));
				
			}
		});
        
        listComplete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventsCenter.getInstance().post(new ListCompleteButtonEvent(Status.STATUS_COMPLETE));
				
			}
		});
        
        
        
      
        DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPanel.datePicker);
        
        //event handler for when you click a specified date on the calendar
        calendarPanel.datePicker.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//gets the value of the selected date
		        date = calendarPanel.datePicker.getValue();
		        
		        //raising an event in the model manager
		        EventsCenter.getInstance().post(new DatePickedOnCalendarEvent(date));
			}
		});
		Node popupContent = datePickerSkin.getPopupContent();
		
		
		
		populateCalendarPanel(placeholder, outerBox, innerBoxTop, innerBoxBottom, list, listAll, listComplete,
				listPending, popupContent);
		
		
		
//        
//        placeholder.getChildren().add(popupContent);
//        placeholder.getChildren().add(list);
        
        return calendarPanel;
    }

	private static void populateCalendarPanel(AnchorPane placeholder, VBox outerBox, HBox innerBoxTop,
			HBox innerBoxBottom, Button list, Button listAll, Button listComplete, Button listPending,
			Node popupContent) {
		placeholder.getChildren().add(outerBox);
		outerBox.getChildren().add(popupContent);
		outerBox.getChildren().add(innerBoxTop);
		innerBoxTop.getChildren().add(list);
		innerBoxTop.getChildren().add(listAll);
		outerBox.getChildren().add(innerBoxBottom);
		innerBoxBottom.getChildren().add(listPending);
		innerBoxBottom.getChildren().add(listComplete);
	}
    
}
