package seedu.task.ui;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

/**
 * The Browser Panel of the App.
 */
public class CalendarPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private DatePicker datePicker;
    DatePickerSkin datePickerSkin;
    private static LocalDate date;
    
//    private DatePickerPopUpDemo datePicker;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a BrowserPanel.
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
        calendarPanel.datePicker.setMaxSize(400, 400);
      
        DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPanel.datePicker);
        
        //gets the value of the selected date
        date = calendarPanel.datePicker.getValue();
        System.out.println(date);
		Node popupContent = datePickerSkin.getPopupContent();
		
		
        
 
        placeholder.getChildren().add(popupContent);
        return calendarPanel;
    }
    
    
    
    
    
    

    
}
