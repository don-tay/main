package nasa.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.NasaBookBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new UserPrefs());
    private Model expectedModel = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new UserPrefs());

    @Test
    void singleUndoTest() {
        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Networks"));

        UndoCommand undoCommand = new UndoCommand();

        undoCommand.execute(model);

        assertEquals(model, expectedModel);
    }

    @Test
    void multipleUndoCheck() {
        //add -> add -> add -> undo -> undo -> undo
        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Networks"));
        model.addModule(new ModuleCode("CS2107"), new ModuleName("Computer Security"));
        model.addModule(new ModuleCode("CS2102"), new ModuleName("Database"));

        UndoCommand undoCommand = new UndoCommand();

        undoCommand.execute(model);
        undoCommand.execute(model);
        undoCommand.execute(model);

        assertEquals(model, expectedModel);
    }

    @Test
    void inBetweenUndoCheck() {
        //add -> undo -> add -> undo
        UndoCommand undoCommand = new UndoCommand();

        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Networks"));
        undoCommand.execute(model);

        model.addModule(new ModuleCode("CS2107"), new ModuleName("Computer Security"));
        undoCommand.execute(model);

        assertEquals(model, expectedModel);
    }
}
