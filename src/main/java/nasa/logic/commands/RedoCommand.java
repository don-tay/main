package nasa.logic.commands;

import nasa.model.Model;

/**
 * Command to handle redo previous action.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": reset data by a single step.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo last action.";
    public static final String SHOWING_HELP_MESSAGE = "No last action to redo.";

    @Override
    public CommandResult execute(Model model) {
        if (model.redoHistory()) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }
}
