package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import nasa.model.Model;
import nasa.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false,
                true, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
