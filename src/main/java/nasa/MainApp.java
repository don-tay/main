package nasa;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import nasa.commons.core.Config;
import nasa.commons.core.LogsCenter;
import nasa.commons.core.Version;
import nasa.commons.exceptions.DataConversionException;
import nasa.commons.util.ConfigUtil;
import nasa.commons.util.StringUtil;
import nasa.logic.Logic;
import nasa.logic.LogicManager;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.ReadOnlyUserPrefs;
import nasa.model.UserPrefs;
import nasa.model.util.SampleDataUtil;
import nasa.storage.JsonNasaBookStorage;
import nasa.storage.JsonUserPrefsStorage;
import nasa.storage.NasaBookStorage;
import nasa.storage.Storage;
import nasa.storage.StorageManager;
import nasa.storage.UserPrefsStorage;
import nasa.ui.Ui;
import nasa.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing NASA ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        NasaBookStorage nasaBookStorage = new JsonNasaBookStorage(userPrefs.getNasaBookFilePath(),
                userPrefs.getHistoryBookFilePath());
        storage = new StorageManager(nasaBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s NASA and {@code userPrefs}. <br>
     * The data from the sample NASA will be used instead if {@code storage}'s NASA is not found,
     * or an empty NASA will be used instead if errors occur when reading {@code storage}'s NASA.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyNasaBook> nasaBookOptional;
        Optional<ReadOnlyHistory> historyBookOptional;
        ReadOnlyNasaBook initialNasaBook;
        ReadOnlyHistory initialHistoryBook;
        try {
            nasaBookOptional = storage.readNasaBook();
            historyBookOptional = storage.readHistoryBook();
            if (!nasaBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample NasaBook");
            }

            if (!historyBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample HistoryBook");
            }
            initialNasaBook = nasaBookOptional.orElseGet(SampleDataUtil::getSampleNasaBook);
            initialHistoryBook = historyBookOptional.orElseGet(SampleDataUtil::getSampleHistoryBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty NasaBook");
            initialNasaBook = new NasaBook();
            initialHistoryBook = new HistoryBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty NasaBook");
            initialNasaBook = new NasaBook();
            initialHistoryBook = new HistoryBook();
        }

        return new ModelManager(initialNasaBook, initialHistoryBook, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     * @param configFilePath Path
     * @return Config
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     * @param storage UserPrefsStorage
     * @return UserPrefs
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty NASA");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting NASA " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping NASA ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
