package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nasa.logic.commands.AddModuleCommand;
import nasa.logic.commands.ClearCommand;
import nasa.logic.commands.Command;
import nasa.logic.commands.ContinueCommand;
import nasa.logic.commands.DeleteActivityCommand;
import nasa.logic.commands.DeleteModuleCommand;
import nasa.logic.commands.DoneCommand;
import nasa.logic.commands.EditActivityCommand;
import nasa.logic.commands.EditModuleCommand;
import nasa.logic.commands.ExitCommand;
import nasa.logic.commands.FindCommand;
import nasa.logic.commands.HelpCommand;
import nasa.logic.commands.ListCommand;
import nasa.logic.commands.QuoteCommand;
import nasa.logic.commands.RedoCommand;
import nasa.logic.commands.RepeatCommand;
import nasa.logic.commands.SortCommand;
import nasa.logic.commands.StatisticsCommand;
import nasa.logic.commands.UndoCommand;
import nasa.logic.commands.addcommands.AddDeadlineCommand;
import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.logic.commands.addcommands.AddLessonCommand;
import nasa.logic.parser.addcommandparser.AddDeadlineCommandParser;
import nasa.logic.parser.addcommandparser.AddEventCommandParser;
import nasa.logic.parser.addcommandparser.AddLessonCommandParser;
import nasa.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class NasaBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord.toLowerCase()) {

        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case AddDeadlineCommand.COMMAND_WORD:
            return new AddDeadlineCommandParser().parse(arguments);

        case DeleteActivityCommand.COMMAND_WORD:
            return new DeleteActivityCommandParser().parse(arguments);

        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case ContinueCommand.COMMAND_WORD:
            return new ContinueCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case EditActivityCommand.COMMAND_WORD:
            return new EditActivityCommandParser().parse(arguments);

        case EditModuleCommand.COMMAND_WORD:
            return new EditModuleCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RepeatCommand.COMMAND_WORD:
            return new RepeatActivityCommandParser().parse(arguments);

        case QuoteCommand.COMMAND_WORD:
            return new QuoteCommand();
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
