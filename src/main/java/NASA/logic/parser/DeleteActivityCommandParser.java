package NASA.logic.parser;

import static java.util.Objects.requireNonNull;

import NASA.logic.commands.DeleteActivityCommand;
import NASA.commons.core.index.Index;
import NASA.logic.commands.DeleteActivityCommand;
import NASA.logic.parser.ParserUtil;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.module.ModuleCode;
import seedu.address.logic.commands.EditCommand;

import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteActivityCommandParser implements Parser<DeleteActivityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        return new DeleteActivityCommand(index, moduleCode);
    }
}