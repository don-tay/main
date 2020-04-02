package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.Arrays;

import nasa.logic.commands.FindCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;
import nasa.model.activity.Name;
import nasa.model.module.ModuleCode;
import nasa.model.module.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE,
                        PREFIX_ACTIVITY_NAME);

        boolean modulePrefixPresent = argMultimap.getValue(PREFIX_MODULE).isPresent();
        boolean activityPrefixPresent = argMultimap.getValue(PREFIX_ACTIVITY_NAME).isPresent();
        boolean resetKeyWord = args.trim().toLowerCase().equals("reset");

        if (!modulePrefixPresent && !activityPrefixPresent && !resetKeyWord) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (modulePrefixPresent) {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            String[] nameKeywords = moduleCode.toString().split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (activityPrefixPresent) {
            Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
            String[] nameKeywords = activityName.toString().split("\\s+");
            return new FindCommand(new ActivityContainsKeyWordsPredicate(Arrays.asList(nameKeywords)));
        } else {
            return new FindCommand();
        }
    }
}
