//@@author A0153723J
package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.task.commons.handlers.ui.ClickListButtonHandler;
import seedu.task.commons.handlers.ui.SelectCalendarDateHandler;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart{

    private static final String FXML = "CalendarPanel.fxml";

    private DatePicker datePicker;
    DatePickerSkin datePickerSkin;
    private AnchorPane placeHolder;
    private GridPane mainPane;
    
    @FXML
    private AnchorPane calendarPane;
    
    @FXML
    private Button listButton;
    
    @FXML
    private Button listAllButton;
    
    @FXML
    private Button listCompleteButton;
    
    /**
     * Factory method for creating a Calendar Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the CalendarPanel must be inserted
     */
    public static CalendarPanel load(Stage primaryStage, AnchorPane calendarPlaceHolder) {
        CalendarPanel calendarPanel = UiPartLoader.loadUiPart(primaryStage, calendarPlaceHolder, new CalendarPanel());
        calendarPanel.configure();
        calendarPanel.addToPlaceholder();
        return calendarPanel;
    }

    private void configure() {
        datePicker = new DatePicker();
        datePicker.setPrefHeight(600);
        datePicker.setStyle("-fx-font-size: 2em;");
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        calendarPane.getChildren().add(popupContent);
        setOnActionListeners();
    }

    private void setOnActionListeners() {
        datePicker.setOnAction(new SelectCalendarDateHandler(datePicker));
        listButton.setOnAction(new ClickListButtonHandler(ClickListButtonHandler.LIST_DEFAULT));
        listAllButton.setOnAction(new ClickListButtonHandler(ClickListButtonHandler.LIST_ALL));
        listCompleteButton.setOnAction(new ClickListButtonHandler(ClickListButtonHandler.LIST_COMPLETE));
    }

    private void addToPlaceholder() {
        placeHolder.getChildren().add(mainPane);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }
    
}
