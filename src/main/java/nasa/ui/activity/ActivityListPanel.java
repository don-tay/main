package nasa.ui.activity;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Activity;
import nasa.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }

    public void setWidth(double width) {
        activityListView.setPrefWidth(width);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);
            ActivityCard temp;
            prefWidthProperty().bind(activityListView.widthProperty().subtract(10));

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                temp = new ActivityCard(activity, getIndex() + 1);
                setGraphic(temp.getRoot());
            }
        }
    }

}
