package nasa.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

/**
 * A wrapper class to handle various history data.
 * @param <T> Type
 */
public class HistoryManager<T> implements HistoryModel<T> {

    private final HistoryBook<T> historyBook;

    public HistoryManager(ReadOnlyHistory<T> historyBook) {
        requireNonNull(historyBook);

        this.historyBook = new HistoryBook<>(historyBook);
    }

    public HistoryManager() {
        this.historyBook = new HistoryBook<>();
    }

    public ObservableList<T> getHistoryList() {
        return historyBook.getModuleListHistory();
    }

    public HistoryBook<T> getHistoryBook() {
        return historyBook;
    }

    public T getItem() {
        return historyBook.getItem();
    }

    @Override
    public void add(T moduleList) {
        historyBook.add(moduleList);
    }

    @Override
    public boolean undo() {
        return historyBook.undo();
    }

    @Override
    public boolean redo() {
        return historyBook.redo();
    }
}
