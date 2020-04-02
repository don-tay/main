package nasa.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.HistoryBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.history.ModuleListHistory;
import nasa.model.module.UniqueModuleList;

/**
 * Jackson friendly use of History.
 */
@JsonRootName(value = "historybook")
public class JsonAdaptedHistory {

    private List<List<JsonAdaptedModule>> moduleList = new ArrayList<>();

    /**
     * Construct History with details of module list.
     * @param moduleList List
     */
    @JsonCreator
    public JsonAdaptedHistory(@JsonProperty("moduleList") List<List<JsonAdaptedModule>> moduleList) {
        if (moduleList != null) {
            this.moduleList = moduleList;
        }
    }

    /**
     * Converts a given module list for Jackson use.
     * @param source ReadOnlyHistory
     */
    public JsonAdaptedHistory(ReadOnlyHistory<UniqueModuleList> source) {
        moduleList.addAll(source.getModuleListHistory().stream()
            .map(x -> x.asUnmodifiableObservableList().stream()
                    .map(JsonAdaptedModule::new).collect(Collectors.toList()))
            .collect(Collectors.toList()));
    }

    /**
     * Converts Jackson to adapted history object.
     * @return HistoryBook
     */
    public HistoryBook<UniqueModuleList> toModelType() throws IllegalValueException {
        final List<UniqueModuleList> historyList = new ArrayList<>();
        for (List<JsonAdaptedModule> list : moduleList) {
            final UniqueModuleList uniqueList = new UniqueModuleList();
            for (JsonAdaptedModule module : list) {
                uniqueList.add(module.toModelType());
            }
            historyList.add(uniqueList);
        }

        final ModuleListHistory<UniqueModuleList> temp = new ModuleListHistory<>();
        temp.setStack(historyList);
        HistoryBook<UniqueModuleList> historyBook = new HistoryBook<>();
        historyBook.setModuleListHistory(temp);
        return historyBook;
    }
}
