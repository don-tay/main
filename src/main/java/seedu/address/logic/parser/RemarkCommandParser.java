package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

//@@author don-tay-reused
//Reused from https://github.com/nus-cs2103-AY1920S1/addressbook-level3/commit/efdcdf0e80cec9489f7b47e3f65824f4688ad8f7#diff-fc19ecee89c3732a62fbc8c840250508
public class RemarkCommandParser implements Parser<RemarkCommand> {

        public RemarkCommand parse(String args) throws ParseException {
            requireNonNull(args);
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                    PREFIX_REMARK);

            Index index;
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemarkCommand.MESSAGE_USAGE), ive);
            }

            String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

            return new RemarkCommand(index, new Remark(remark));
        }
}
//@@author
