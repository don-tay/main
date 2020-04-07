package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.Optional;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.commons.util.CollectionUtil;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.activity.Status;
import nasa.model.activity.UniqueActivityList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

/**
 * Edits a specific activity in the moduleCode's list.
 */
public class EditActivityCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the displayed moduleCode's activity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2030 "
            + "1 "
            + PREFIX_DATE + "2020-03-20 "
            + PREFIX_ACTIVITY_NAME + "Assignment 2.3";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the "
            + "moduleCode activity list.";

    private final Index index;
    private final ModuleCode moduleCode;
    private final EditActivityCommand.EditActivityDescriptor editActivityDescriptor;

    /**
     * Creates an EditActivityCommand to edit an activity
     * with the specified {@code index} from the specified {@code moduleCode} list.
     * @param index index of the activity item as specified in the corresponding module
     * @param moduleCode module code which the activity item is found in
     * @param editActivityDescriptor
     */
    public EditActivityCommand(Index index, ModuleCode moduleCode, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(index);
        this.index = index;
        this.moduleCode = moduleCode;
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module module = model.getNasaBook().getUniqueModuleList().getModule(moduleCode);

        requireNonNull(module);

        UniqueActivityList moduleUniqueActivityList = module.getActivities();

        if (index.getZeroBased() >= moduleUniqueActivityList.getActivityList().size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToEdit = moduleUniqueActivityList.getActivityByIndex(index);
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

        requireNonNull(editedActivity);

        if (!activityToEdit.isSameActivity(editedActivity) && model.hasActivity(moduleCode, editedActivity)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.setActivityByIndex(moduleCode, index, editedActivity);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        model.updateFilteredActivityList(index, PREDICATE_SHOW_ALL_ACTIVITIES);

        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }

    /**
     * Creates and returns an {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                  EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(activityToEdit);

        Name updatedName = editActivityDescriptor.getName().orElse(activityToEdit.getName());
        Date updatedDate = editActivityDescriptor.getDate().orElse(activityToEdit.getDate());
        Note updatedNote = editActivityDescriptor.getNote().orElse(activityToEdit.getNote());
        Priority updatedPriority = editActivityDescriptor.getPriority().orElse(activityToEdit.getPriority());
        Status status = activityToEdit.getStatus(); // original moduleCode's activity list is preserved

        if (activityToEdit instanceof Deadline) {
            Date dueDate = ((Deadline) activityToEdit).getDueDate();
            return new Deadline(updatedName, updatedDate, updatedNote, status, updatedPriority, dueDate);
        } else if (activityToEdit instanceof Event) {
            Date startDate = ((Event) activityToEdit).getDateFrom();
            Date endDate = ((Event) activityToEdit).getDateTo();
            return new Event(updatedName, updatedDate, updatedNote, status, updatedPriority,
                    startDate, endDate);
        } else if (activityToEdit instanceof Lesson) {
            Date startDate = ((Lesson) activityToEdit).getDateFrom();
            Date endDate = ((Lesson) activityToEdit).getDateTo();
            return new Event(updatedName, updatedDate, updatedNote, status, updatedPriority,
                    startDate, endDate);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditActivityCommand // instanceof handles nulls
                && index.equals(((EditActivityCommand) other).index)
                && moduleCode.equals(((EditActivityCommand) other).moduleCode)
                && editActivityDescriptor.equals(((EditActivityCommand) other).editActivityDescriptor));
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the moduleCode.
     */
    public static class EditActivityDescriptor {
        private Name name;
        private Date date;
        private Note note;
        private Priority priority;

        public EditActivityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditActivityDescriptor(EditActivityCommand.EditActivityDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setNote(toCopy.note);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, note, priority);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityCommand.EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityCommand.EditActivityDescriptor e = (EditActivityCommand.EditActivityDescriptor) other;

            return getName().equals(e.getName()) && getDate().equals(e.getDate()) && getNote().equals(e.getNote())
                    && getPriority().equals(e.getPriority());
        }
    }
}
