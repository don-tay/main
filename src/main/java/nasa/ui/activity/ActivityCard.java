package nasa.ui.activity;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.ui.UiPart;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "ActivityListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;
    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label dateline;
    @FXML
    private Label date;
    @FXML
    private Label note;
    @FXML
    private Label status;
    @FXML
    private Label priority;
    @FXML
    private Group type;
    @FXML
    private Label labelForCircle;
    @FXML
    private Circle circle;



    public ActivityCard(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        name.setText(activity.getName().toString());
        date.setText(activity.getDate().toString());
        note.setText(activity.getNote().toString());
        status.setText(activity.getStatus().toString());
        priority.setText(activity.getPriority().toUiString());

        setPriorityColour();

        if (activity instanceof Deadline) {
            Deadline deadline = (Deadline) activity;
            labelForCircle.setText("D");
            dateline.setText(deadline.getDueDate().toString());
            int urgent = deadline.getDifferenceInDate();
            if (urgent > 5) {
                circle.setFill(Color.GREEN);
            } else if (urgent > 3) {
                circle.setFill(Color.YELLOW);
            } else {
                circle.setFill(Color.RED);
            }
        } else if (activity instanceof Event) {
            labelForCircle.setText("E");
            dateline.setVisible(false);
        } else {
            labelForCircle.setText("L");
            dateline.setVisible(false);
        }
    }

    private void setPriorityColour() {
        ObservableList<String> priorityStyle = priority.getStyleClass();
        switch (priority.getText()) {
        case "!":
            priority.setStyle("-fx-text-fill:#00bc2f;");
            break;
        case "!!":
            priority.setStyle("-fx-text-fill:#3dba00;");
            break;
        case "!!!":
            priority.setStyle("-fx-text-fill:#d0d000;");
            break;
        case "!!!!":
            priority.setStyle("-fx-text-fill:#e1b400;");
            break;
        case "!!!!!":
            priority.setStyle("-fx-text-fill:#e80303;");
            break;
        default:
            priority.setStyle("");
            break;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCard)) {
            return false;
        }

        // state check
        ActivityCard card = (ActivityCard) other;
        return name.getText().equals(card.name.getText());
    }
}
