package nasa.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Activity;
import nasa.model.module.Module;

/**
 * Panel showing statistics on modules.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;



    public StatisticsPanel(ObservableList<Module> moduleObservableList) {
        super(FXML);

        loadStatistics(moduleObservableList);

        moduleObservableList.addListener(new ListChangeListener<Module>() {
            @Override
            public void onChanged(Change<? extends Module> c) {
                resetStatistics();
                loadStatistics(moduleObservableList);
                updateStatistics(moduleObservableList);
            }
        });
        updateStatistics(moduleObservableList);
    }


    /**
     * Method to update statistics as activities are edited/removed/added.
     * @param moduleObservableList List of modules
     */
    private void updateStatistics(ObservableList<Module> moduleObservableList) {
        for (Module module : moduleObservableList) {
            ObservableList<Activity> activityObservableList = module.getFilteredActivityList();
            activityObservableList.addListener(new ListChangeListener<Activity>() {
                @Override
                public void onChanged(Change<? extends Activity> c) {
                    resetStatistics();
                    loadStatistics(moduleObservableList);
                }
            });
        }
    }

    /**
     * Set statistics.
     * @param moduleList
     */

    private void loadStatistics(ObservableList<Module> moduleList) {

        List<PieChart.Data> pieData = new ArrayList<>();
        for (Module module : moduleList) {
            pieData.add(new PieChart.Data(module.getModuleCode().toString(),
                    module.getFilteredActivityList().size()));
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(pieData);
        pieChart.setData(chartData);
        chartData.forEach(data ->
                data.nameProperty().bind(data.pieValueProperty().getValue() > 1
                        ? Bindings.concat(
                                data.getName(), " - ", data.pieValueProperty().intValue(), " activities"
                        )
                        : Bindings.concat(
                                data.getName(), " - ", data.pieValueProperty().intValue(), " activity"
                        )
                )
        );

        //Bar chart
        XYChart.Series<String, Integer> barData = new XYChart.Series();
        for (Module module : moduleList) {
            barData.getData().add(new XYChart.Data(module.getModuleCode().toString(),
                    module.getFilteredActivityList().size()));
        }
        barChart.setData(FXCollections.observableArrayList(barData));

    }

    private void resetStatistics() {
        pieChart.getData().clear();
        barChart.getData().clear();
    }
}
