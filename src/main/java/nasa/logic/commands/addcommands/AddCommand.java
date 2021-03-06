package nasa.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;

import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.module.ModuleCode;

/**
 * Adds any activity to a module's activity list.
 */
public class AddCommand extends Command {
    public static final String MESSAGE_SUCCESS = "New activity added!";

    public static final String MESSAGE_DUPLICATED_ACTIVITY =
        "This activity already exist in the module's activity list!";

    public static final String MESSAGE_MODULE_NOT_FOUND =
        "The module does not exist!";


    private final Activity toAdd;
    private final ModuleCode moduleCode;

    /**
     * Creates an AddCommand that adds {@code activity} to list of {@code moduleCode}.
     * @param activity Activity to be added
     * @param moduleCode Module where the activity is to be added
     */
    public AddCommand(Activity activity, ModuleCode moduleCode) {
        requireNonNull(activity);
        requireNonNull(moduleCode);
        toAdd = activity;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check if module exist and if a duplicated activity already exists
        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        if (model.hasActivity(moduleCode, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATED_ACTIVITY);
        }

        model.addActivity(moduleCode, toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd)
                && moduleCode.equals(((AddCommand) other).moduleCode));
    }
}
