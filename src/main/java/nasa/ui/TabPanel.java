package nasa.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import nasa.commons.core.LogsCenter;
import nasa.logic.Logic;

/**
 * Tab for modules.
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TabPanel.class);

    private ModuleListPanel moduleListPanel;
    private StatisticsPanel statisticsPanel;
    private CalendarView calendarView;

    @FXML
    private StackPane moduleListPanelPlaceholder;
    @FXML
    private StackPane statisticsPanelPlaceholder;
    @FXML
    private StackPane calendarPlaceholder;
    @FXML
    private HBox moduleList;
    @FXML
    private TabPane tabPane;
    @FXML
    private HBox statisticsList;

    public TabPanel(Logic logic) {
        super(FXML);
        calendarView = new CalendarView(logic.getFilteredModuleList());
        calendarPlaceholder.getChildren().add(calendarView.getRoot());
        calendarPlaceholder.getStyleClass().add("background");
        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        statisticsPanel = new StatisticsPanel(logic.getFilteredModuleList());
        statisticsPanelPlaceholder.getChildren().add(statisticsPanel.getRoot());
        tabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            tabPane.setTabMinWidth(tabPane.getWidth() / 4);
            tabPane.setTabMaxWidth(tabPane.getWidth() / 4);
        });
    }

    public void getStatistics() {
        tabPane.getSelectionModel().select(2);
    }

    /**
     * Switches to the subsequent tab or the first tab if current tab is the last tab.
     */
    public void next() {
        if (tabPane.getSelectionModel().isSelected(tabPane.getTabs().size() - 1)) {
            tabPane.getSelectionModel().selectFirst();
        } else {
            tabPane.getSelectionModel().selectNext();
        }
    }
}
