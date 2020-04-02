package nasa.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.LogsCenter;
import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.logic.parser.NasaBookParser;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.Model;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NasaBookParser nasaBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        nasaBookParser = new NasaBookParser();
        try {
            storage.saveUltimate(model.getNasaBook(), model.getHistoryManager().getHistoryBook());
        } catch (IOException ioe) {
            logger.info("-------------------Error while setting up logic manager");
        }
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = nasaBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveUltimate(model.getNasaBook(), model.getHistoryManager().getHistoryBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyNasaBook getNasaBook() {
        return model.getNasaBook();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    public Module getModule(ModuleCode moduleCode) {
        return model.getModule(moduleCode);
    }

    @Override
    public Path getNasaBookFilePath() {
        return model.getNasaBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
