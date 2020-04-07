package nasa.logic.parser;

import static nasa.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import nasa.logic.commands.StatisticsCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;

/**
 * Parses input arguments and creates an Statistics object.
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatisticsCommand
     * and returns an StatisticsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (arePrefixesPresent(argMultimap, PREFIX_TIME)) {
            String activityPredicate = ParserUtil.parseStatistics(argMultimap.getValue(PREFIX_TIME).get());
            switch (activityPredicate) {
            case "deadline":
                return new StatisticsCommand(activity -> activity instanceof Deadline);
            case "event":
                return new StatisticsCommand(activity -> activity instanceof Event);
            case "lesson":
                return new StatisticsCommand(activity -> activity instanceof Lesson);
            default:
                return new StatisticsCommand(null);
            }
        }
        return new StatisticsCommand(null);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
