package seedu.task.ui;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.DatePickedOnCalendarEvent;
import seedu.task.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.commands.*;

import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private UserCommandLog userCommandLog = new UserCommandLog();

    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, ResultDisplay resultDisplay,
            Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    @FXML
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        previousCommandTest = commandTextField.getText();

        userCommandLog.addCommandToUserLog(previousCommandTest);

        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

    // @@author A0153658W
    /**
     * Sets up key listeners for both the Up arrow and Down arrow
     */
    public void setArrowKeyListener() {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.UP)) {
                    // get previous command
                    String previousCommand = userCommandLog.getPreviousCommand();
                    commandTextField.setText(previousCommand);

                    // move cursor caret to the end of the line
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            commandTextField.positionCaret(previousCommand.length());
                        }
                    });
                } else if (ke.getCode().equals(KeyCode.DOWN)) {
                    // get the next command
                    String nextCommand = userCommandLog.getNextCommand();
                    commandTextField.setText(nextCommand);

                    // move cursor caret to the end of the line
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            commandTextField.positionCaret(nextCommand.length());
                        }
                    });
                }
            }
        });
    }

}
