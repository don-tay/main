package nasa.ui.activity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import nasa.model.activity.Activity;
import nasa.model.activity.Lesson;
import nasa.ui.UiPart;


/**
 * An UI component that displays information of a {@code Module}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;
    @FXML
    private GridPane lessonPane;
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



    public LessonCard(Lesson activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        name.setText(activity.getName().toString());
        startDate.setText("From " + activity.getDateFrom().toString());
        endDate.setText("To " + activity.getDateTo().toString());
        note.setText(activity.getNote().toString());
        status.setText(activity.getStatus().toString());
        priority.setText("Priority: " + activity.getPriority().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonCard)) {
            return false;
        }

        // state check
        LessonCard card = (LessonCard) other;
        return name.getText().equals(card.name.getText());
    }
}
