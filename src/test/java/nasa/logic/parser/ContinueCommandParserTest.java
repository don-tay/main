package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.ContinueCommand;
import nasa.model.module.ModuleCode;

public class ContinueCommandParserTest {

    private ContinueCommandParser parser = new ContinueCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1231);

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        assertParseSuccess(parser, "1" + MODULE_CODE_DESC_CS1231, new ContinueCommand(Index.fromOneBased(1),
            moduleCode));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //no module stated in the user input
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ContinueCommand.MESSAGE_USAGE));

        // no index stated at all
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ContinueCommand.MESSAGE_USAGE));

        // invalid module code
        assertParseFailure(parser, "1" + INVALID_MODULE_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContinueCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "-4" + MODULE_NAME_DESC_CS1231,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContinueCommand.MESSAGE_USAGE));
    }
}
