package NASA.logic.parser.addcommandparser;

import NASA.logic.commands.addcommands.AddEventCommand;
import NASA.logic.parser.ArgumentMultimap;
import NASA.logic.parser.ArgumentTokenizer;
import NASA.logic.parser.ParserUtil;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.activity.Date;
import NASA.model.activity.Event;
import NASA.model.activity.Name;
import NASA.model.activity.Note;
import NASA.model.module.ModuleCode;

import static NASA.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static NASA.logic.parser.CliSyntax.*;

public class AddEventCommandParser extends AddCommandParser {

    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_FROM_DATE, PREFIX_TO_DATE, PREFIX_ACTIVITY_NAME,
                        PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TO_DATE, PREFIX_FROM_DATE, PREFIX_ACTIVITY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Date toDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TO_DATE).get());
        Date fromDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_FROM_DATE).get());
        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Event event = new Event(activityName, fromDate, toDate, note);

        return new AddEventCommand(event, moduleCode);
    }
}
