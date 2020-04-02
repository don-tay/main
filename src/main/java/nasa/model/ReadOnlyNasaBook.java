package nasa.model;

import javafx.collections.ObservableList;

import nasa.model.module.Module;
import nasa.model.module.UniqueModuleList;

/**
 * Unmodifiable view of an Nasa book
 */
public interface ReadOnlyNasaBook {

    /**
     * Gets the underlying {@code UniqueModuleList} from the NasaBook.
     * @return UniqueModuleList
     */
    UniqueModuleList getUniqueModuleList();
    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     * @return ObservableList
     */
    ObservableList<Module> getModuleList();

    ObservableList<Module> getDeepCopyList();
}
