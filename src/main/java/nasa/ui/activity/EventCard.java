package nasa.ui.activity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import nasa.model.activity.Event;
import nasa.ui.UiPart;


/**
 * An UI component that displays information of a {@code Module}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event activity;
    @FXML
    private GridPane eventPane;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label note;
    @FXML
    private Label status;
    @FXML
    private Label priority;



    public EventCard(Event activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        name.setText(activity.getName().toString());
        startDate.setText("From " + activity.getDateFrom().toString());
        endDate.setText("To " + activity.getDateTo().toString());
        note.setText(activity.getNote().toString());
        status.setText(activity.getStatus().toString());
        priority.setText("Priority: " + activity.getPriority().toUiString());

        setPriorityColour();
    }

    /**
     * Sets font colour of the priority indicator.
     */
    private void setPriorityColour() {
        switch (priority.getText()) {
            case "!":
                priority.setStyle("-fx-text-fill:#00bc6b;");
                break;
            case "!!":
                priority.setStyle("-fx-text-fill:#85ba00;");
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
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return name.getText().equals(card.name.getText());
    }
}
