package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

/**
 * Sets an activity to undone.
 */
public class ContinueCommand extends Command {
    public static final String COMMAND_WORD = "continue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sets index of activity in module to being undone or incomplete.\n"
        + "Parameters:" + "INDEX" + PREFIX_MODULE + "MODULE CODE\n"
        + "Example" + COMMAND_WORD + " 2 " + PREFIX_MODULE + "CS2030";

    public static final String MESSAGE_SUCCESS = "Activity set to undone!";

    public static final String MESSAGE_ACTIVITY_NOT_FOUND =
        "Activity not found in module";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module not found!";

    public static final String MESSAGE_ACTIVITY_ALREADY_UNDONE = "Activity is already undone!";

    private Index index;
    private ModuleCode moduleCode;

    public ContinueCommand(Index index, ModuleCode moduleCode) {
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        } else {
            // get module and check if the activity index exist
            Module module = model.getModule(moduleCode);
            if (index.getZeroBased() > module.getActivities().getActivityList().size()) {
                throw new CommandException(MESSAGE_ACTIVITY_NOT_FOUND);
            }

            // check if activity already done
            Activity activity = module.getActivityByIndex(index);
            if (!activity.isDone()) {
                throw new CommandException(MESSAGE_ACTIVITY_ALREADY_UNDONE);
            } else {
                activity.setUndone();
                return new CommandResult(String.format(MESSAGE_SUCCESS, activity));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ContinueCommand // instanceof handles nulls
            && index.equals(((ContinueCommand) other).index)
            && moduleCode.equals(moduleCode)); // state check
    }
}
