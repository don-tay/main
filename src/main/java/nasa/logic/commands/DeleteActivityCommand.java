package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.ModuleCode;

/**
 * Deletes an activity identified using it's displayed index from the module list.
 */
public class DeleteActivityCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activities identified by the index numbers used in the displayed activity list.\n"
            + "Parameters: " + "INDEX (must be a positive integer)\n" + PREFIX_MODULE + "MODULE CODE"
            + "Example: " + COMMAND_WORD + " 1 2 3" + PREFIX_MODULE + "CS3233";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = " are deleted successfully!";

    public static final String MESSAGE_FAILURE = "Activity indicated does not exist!";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module does not exist!";

    private final Index index;
    private final ModuleCode moduleCode;

    /**
     * Creates a DeleteActivityCommand to delete the activity at
     * {@code index} from the specified {@code moduleCode} list.
     * @param index index of the activity to be deleted
     * @param moduleCode module of the activity that it is associated with
     */
    public DeleteActivityCommand(Index index, ModuleCode moduleCode) {
        requireNonNull(index);
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        try {
            model.removeActivityByIndex(moduleCode, index);
            return new CommandResult(index.toString() + MESSAGE_DELETE_ACTIVITY_SUCCESS);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteActivityCommand // instanceof handles nulls
                && index.equals(((DeleteActivityCommand) other).index));
    }
}
