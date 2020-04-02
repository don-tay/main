package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.DoneCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parses input arguments and creates a DoneCommand object.
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        ModuleCode moduleCode;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new DoneCommand(index, moduleCode);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneCommand.MESSAGE_USAGE));
        }
    }
}
