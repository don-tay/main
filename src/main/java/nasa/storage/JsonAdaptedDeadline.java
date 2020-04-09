package nasa.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nasa.commons.exceptions.IllegalValueException;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.activity.Schedule;

/**
 * Jackson-friendly version of {@link Deadline}.
 */
class JsonAdaptedDeadline {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";

    private final String name;
    private final String date;
    private final String note;
    private final String priority;
    private final String dueDate;
    private final String schedule;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("name") String name,
                               @JsonProperty("date") String date, @JsonProperty("note") String note,
                               @JsonProperty("priority") String priority,
                               @JsonProperty("dueDate") String dueDate,
                               @JsonProperty("schedule") String schedule) {
        this.name = name;
        this.date = date;
        this.note = note;
        this.priority = priority;
        this.dueDate = dueDate;
        this.schedule = schedule;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        name = source.getName().name;
        date = source.getDateCreated().toString();
        note = source.getNote().toString();
        priority = source.getPriority().toString();
        dueDate = source.getDueDate().toString();
        schedule = source.getSchedule().toString();
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Deadline} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity.
     */
    public Deadline toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }

        final Note modelNote = new Note(note);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriorityValue(priority)) {
            throw new IllegalValueException("");
        }
        final Priority modelPriority = new Priority(priority);

        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(dueDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDueDate = new Date(dueDate);

        final boolean modelIsDone = false;

        Schedule modelSchedule = new Schedule(schedule);

        Deadline deadline = new Deadline(modelName, modelDate, modelNote, modelPriority, modelDueDate, modelIsDone);
        deadline.setSchedule(modelSchedule);

        return deadline;
    }
}
