package nasa.model.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that manage all histories.
 * @param <T> Type
 */
public class ModuleListHistory<T> extends History<T> {

    public ModuleListHistory() {
        super();
    }

    public ModuleListHistory(Stack<T> moduleListStack) {
        super(moduleListStack);
    }

    /**
     * Return unmodifiable list containing histories.
     * @return ObservableList
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        List<T> list = new ArrayList<>(super.getStack());
        ObservableList<T> newList = FXCollections.observableArrayList(list);
        return newList;
    }

}
