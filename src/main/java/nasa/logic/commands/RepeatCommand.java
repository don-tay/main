package nasa.logic.commands;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Name;
import nasa.model.module.ModuleCode;

/**
 * Repeat an activity for a week, twice a week or monthly.
 */
public class RepeatCommand extends Command {

    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": repeat a module activity.\n"
            + "Example to cancel a repetition: " + COMMAND_WORD + " m/CS3233 a/SEA Group Programming Assignment r/0\n"
            + "Example for weekly repetition: " + COMMAND_WORD + " m/CS3233 a/SEA Group Programming Assignment r/1\n"
            + "Example for twice weekly repetition: " + COMMAND_WORD + " m/CS3233 a/Lab r/2\n"
            + "Example for monthly repetition: " + COMMAND_WORD + " m/CS3233 a/Lab r/3";

    public static final String MESSAGE_SUCCESS = "Successfully added a repeat!";

    public static final String MESSAGE_FAILURE = "Unsuccessfully added. Please check for correctness in module code or"
            + "activity name.";

    public static final String MESSAGE_NO_MODULE = "No such module, try again with a correct module.";

    public static final String MESSAGE_NO_ACTIVITY = "No such activity, try again with a correct activity.";

    private ModuleCode module;
    private Name activity;
    private Index schedule;

    public RepeatCommand(ModuleCode module, Name activity, Index schedule) {
        requireAllNonNull(module, activity, schedule);

        this.module = module;
        this.activity = activity;
        this.schedule = schedule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_NO_MODULE);
        } else {
            if (!model.hasActivity(module, activity)) {
                throw new CommandException(MESSAGE_NO_ACTIVITY);
            }
        }

        if (model.setSchedule(module, activity, schedule)) {
            return new CommandResult(String.format("%s for %s %s %s", MESSAGE_SUCCESS, module, activity,
                    schedule.getZeroBased()),
                    false, false);
        } else {
            return new CommandResult(MESSAGE_FAILURE, false, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RepeatCommand
                && module.equals(((RepeatCommand) other).module)
                && activity.equals(((RepeatCommand) other).activity)
                && schedule.equals(((RepeatCommand) other).schedule));
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", module, activity, schedule.getZeroBased());
    }
}
