package nasa.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import nasa.model.history.ModuleListHistory;

/**
 * Construct a class to store undo and redo history.
 * @param <T> Type
 */
public class HistoryBook<T> implements ReadOnlyHistory<T> {

    private ModuleListHistory<T> moduleListHistory;
    private ModuleListHistory<T> moduleListHistoryCollector;

    {
        moduleListHistory = new ModuleListHistory<>();
        moduleListHistoryCollector = new ModuleListHistory<>();
    }

    public HistoryBook() {}

    public HistoryBook(ReadOnlyHistory<T> history) {
        this();
        resetData(history);
    }

    /**
     * Reset history with new data.
     * @param newData Date
     */
    public void resetData(ReadOnlyHistory<T> newData) {
        requireNonNull(newData);

        moduleListHistory.setStack(newData.getModuleListHistory());
    }

    public ObservableList<T> getModuleListHistory() {
        return moduleListHistory.asUnmodifiableObservableList();
    }

    public void setModuleListHistory(ModuleListHistory<T> moduleListHistory) {
        this.moduleListHistory = moduleListHistory;
    }

    /**
     * Add recent action into history.
     * @param item T
     */
    public void add(T item) {
        moduleListHistory.push(item);
        moduleListHistoryCollector.resetItem();
    }

    /**
     * Undo previous action.
     * @return true if there is history
     */
    public boolean undo() {
        boolean checkStack = !moduleListHistory.isEmpty();
        if (checkStack) {
            moduleListHistoryCollector.pushDirectly(moduleListHistory.getPop());
            moduleListHistory.pop();
        }
        return checkStack;
    }

    /**
     * Redo previous action.
     * @return true if there is undo history
     */
    public boolean redo() {
        boolean checkItem = !moduleListHistoryCollector.isEmpty();
        if (checkItem) {
            moduleListHistory.push(moduleListHistoryCollector.popDirectly());
        }
        return checkItem;
    }

    public T getItem() {
        return moduleListHistory.getPop();
    }

    public T getUndoItem() {
        return moduleListHistoryCollector.getPop();
    }

}
