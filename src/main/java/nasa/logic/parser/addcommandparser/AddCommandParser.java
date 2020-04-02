package nasa.logic.parser.addcommandparser;

import java.util.stream.Stream;

import nasa.logic.commands.addcommands.AddCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.Parser;
import nasa.logic.parser.Prefix;

/**
 * Parses input arguments and creates an AddCommandParser object.
 */
public abstract class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     * @param argumentMultimap ArgumentMultimap
     * @param prefixes Prefix...
     * @return boolean
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
