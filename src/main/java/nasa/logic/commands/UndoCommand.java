package nasa.logic.commands;

import nasa.model.Model;

/**
 * Command to undo the previous action.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": reset data by a single step.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Undo last action.";

    @Override
    public CommandResult execute(Model model) {
        model.undoHistory();
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
