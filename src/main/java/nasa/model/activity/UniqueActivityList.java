package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.index.Index;
import nasa.model.activity.exceptions.ActivityNotFoundException;
import nasa.model.activity.exceptions.DuplicateActivityException;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 * A activity is considered unique by comparing using {@code Activity#isSameActivity(Activity)}.
 * As such, adding and updating of activity uses Activity#isSameActivity(Activity)
 * for equality so as to ensure that the activity being added or updated is
 * unique in terms of identity in the UniqueActivityList. However, the removal of a activity uses
 * Activity#equals(Object) so as to ensure that the activity with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#isSameActivity(Activity)
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
    private final ObservableList<Activity> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     * @param toCheck Activity.
     * @return boolean.
     */
    public boolean contains(Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an activity with {@code name}.
     * @param name Name.
     * @return boolean.
     */
    public boolean hasActivity(Name name) {
        requireNonNull(name);
        return internalList.stream().anyMatch(x -> x.getName().equals(name));
    }

    /**
     * Adds a activity to the list.
     * The activity must not already exist in the list.
     * @param toAdd Activity.
     */
    public void add(Activity toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the list.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity in the list.
     * @param target Activity selected to edit.
     * @param editedActivity Activity to edit.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.isSameActivity(editedActivity) && contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);
    }

    public ObservableList<Activity> getActivityList() {
        return this.internalList;
    }

    public Activity getActivityByIndex(Index index) {
        return internalList.get(index.getZeroBased());
    }

    public Activity getActivityByName(Name name) {
        return internalList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public void setActivityByIndex(Index index, Activity activity) {
        requireNonNull(activity);

        internalList.set(index.getZeroBased(), activity);
    }

    /**
     * Edits activity based on index.
     * @param index index of activity in list
     * @param args parameters for editing
     */
    public void editActivityByIndex(Index index, Object... args) {
        requireAllNonNull(args);

        Activity activity = internalList.get(index.getZeroBased());

        for (Object object : args) {
            if (object instanceof Note) {
                Note note = (Note) object;
                activity.setNote(note);
            }

            if (object instanceof Name) {
                Name name = (Name) object;
                activity.setName(name);
            }

            if (activity instanceof Deadline
                    && object instanceof Date) {
                Date extendDateLine = (Date) object;
                activity.setDate(extendDateLine);
            }
        }

        internalList.set(index.getZeroBased(), activity);
    }

    /**
     * Removes the equivalent activity from the list.
     * The activity must exist in the list.
     * @param toRemove Activity.
     */
    public void remove(Activity toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ActivityNotFoundException();
        }
    }

    /**
     * Removes the equivalent activity from the list by index.
     * The activity must exist in the list.
     * @param index Index.
     */
    public void removeByIndex(Index index) {
        requireNonNull(index);
        internalList.remove(index.getZeroBased());
    }

    /**
     * Empty all the activity inside the list.
     */
    public void removeAll() {
        internalList.clear();
    }

    /**
     * Reset activities with the given list.
     * @param replacement UniqueActivityList
     */
    public void setActivities(UniqueActivityList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code activities}.
     * {@code activities} must not contain duplicate activities.
     * @param activities List
     */
    public void setActivities(List<Activity> activities) {
        requireAllNonNull(activities);
        if (!activitiesAreUnique(activities)) {
            throw new DuplicateActivityException();
        }

        internalList.setAll(activities);
    }

    public ObservableList<Activity> getDeepCopyList() {
        ObservableList<Activity> deepCopyList = FXCollections.observableArrayList();
        for (Activity activity : internalUnmodifiableList) {
            if (activity instanceof Deadline) {
                Activity activityTemp = new Deadline(activity.getName(), activity.getDate(), activity.getNote(),
                        activity.getStatus(), activity.getPriority(), ((Deadline) activity).getDueDate());
                activityTemp.setSchedule(activity.getSchedule());
                deepCopyList.add(activityTemp);
            }

            if (activity instanceof Event) {
                Activity activityTemp = new Event(activity.getName(), activity.getDate(), activity.getNote(),
                        activity.getStatus(), activity.getPriority(), (
                                (Event) activity).getDateFrom(), ((Event) activity).getDateTo());
                activityTemp.setSchedule(activity.getSchedule());
                deepCopyList.add(activityTemp);
            }

            if (activity instanceof Lesson) {
                Activity activityTemp = new Lesson(activity.getName(), activity.getDate(), activity.getNote(),
                        activity.getStatus(), activity.getPriority(), (
                                (Lesson) activity).getDateFrom(), ((Lesson) activity).getDateTo());
                activityTemp.setSchedule(activity.getSchedule());
                deepCopyList.add(activityTemp);
            }
        }
        return deepCopyList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * @return ObservableList
     */
    public ObservableList<Activity> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code activities} contains only unique activities.
     */
    private boolean activitiesAreUnique(List<Activity> activities) {
        for (int i = 0; i < activities.size() - 1; i++) {
            for (int j = i + 1; j < activities.size(); j++) {
                if (activities.get(i).equals(activities.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
