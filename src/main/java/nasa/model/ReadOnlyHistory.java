package nasa.model;

import javafx.collections.ObservableList;

/**
 * API for History book.
 * @param <T>
 */
public interface ReadOnlyHistory<T> {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<T> getModuleListHistory();
}
