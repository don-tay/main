package nasa.testutil;

import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.UniqueActivityList;
import nasa.model.activity.UniqueDeadlineList;
import nasa.model.activity.UniqueEventList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_NAME = "SOFTWARE ENGINEERING";
    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final Deadline DEADLINE_1 = new DeadlineBuilder()
            .withName("Homework")
            .withDueDate("06-06-2021 01:00")
            .withNote("Do models for project")
            .build();
    public static final Deadline DEADLINE_2 = new DeadlineBuilder()
            .withName("Assignment")
            .withDueDate("02-08-2021 01:00")
            .withNote("Do tutorials")
            .build();
    public static final Deadline DEADLINE_3 = new DeadlineBuilder()
            .withName("Prepare group meeting")
            .withDueDate("22-06-2021 01:00")
            .withNote("Prepare for group meetings")
            .build();
    public static final Deadline DEADLINE_4 = new DeadlineBuilder()
            .withName("Lab")
            .withDueDate("13-06-2021 01:00")
            .withNote("Chapter 1")
            .build();
    public static final Event EVENT_1 = new EventBuilder()
            .withName("Competition")
            .withFromDate("06-06-2021 01:00")
            .withToDate("08-06-2021 01:00")
            .withNote("Booked MPSH")
            .build();
    public static final Event EVENT_2 = new EventBuilder()
            .withName("Presentation")
            .withFromDate("06-05-2020 01:00")
            .withToDate("07-05-2020 02:00")
            .withNote("Revise on notes")
            .build();
    public static final Event EVENT_3 = new EventBuilder()
            .withName("Recitation Lesson")
            .withFromDate("08-08-2020 11:00")
            .withToDate("12-08-2020 12:00")
            .withNote("Study tutorial")
            .build();

    private ModuleName name;
    private ModuleCode code;
    private UniqueDeadlineList uniqueDeadlineList;
    private UniqueEventList uniqueEventList;
    private Module module;

    public ModuleBuilder() {
        name = new ModuleName(DEFAULT_MODULE_NAME);
        code = new ModuleCode(DEFAULT_MODULE_CODE);
        uniqueDeadlineList.add(DEADLINE_1);
        uniqueDeadlineList.add(DEADLINE_2);
        uniqueDeadlineList.add(DEADLINE_3);
        uniqueDeadlineList.add(DEADLINE_4);
        uniqueEventList.add(EVENT_1);
        uniqueEventList.add(EVENT_2);
        uniqueEventList.add(EVENT_3);
        module = new Module(code, name);
        module.setDeadlines(uniqueDeadlineList);
        module.setEvents(uniqueEventList);
    }

    public ModuleBuilder(Module moduleToCopy) {
        module = moduleToCopy.getDeepCopyModule();
    }

    /**
     * Sets the {@code ModuleName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = new ModuleName(name);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new ModuleCode(code);
        return this;
    }

    public ModuleBuilder addDeadline(Deadline deadline) {
        this.uniqueDeadlineList.add(deadline);
        return this;
    }

    public ModuleBuilder addEvent(Event event) {
        this.uniqueEventList.add(event);
        return this;
    }

    /**
     * Remove every activity.
     * @return ModuleBuilder
     */
    public ModuleBuilder withRemoveAll() {
        this.uniqueDeadlineList.removeAll();
        this.uniqueEventList.removeAll();
        return this;
    }

    /**
     * Build module based on code and name.
     * @return Module
     */
    public Module build() {
       return module;
    }

}
