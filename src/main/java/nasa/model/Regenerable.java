package nasa.model;

/**
 * Allows activities to auto-repeat.
 * @param <T> Type
 */
public interface Regenerable<T> {
    T regenerate();
}
