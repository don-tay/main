package nasa.logic.commands;

import nasa.model.Model;

/**
 * Command class to activate quote.
 */
public class QuoteCommand extends Command {

    public static final String COMMAND_WORD = "quote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": quote.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        String quoteMessage = model.quote();
        return new CommandResult(quoteMessage);
    }
}
