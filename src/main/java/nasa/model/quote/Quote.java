package nasa.model.quote;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.LogsCenter;

/**
 * Class to store motivation quotes.
 */
public class Quote {

    /**
     * Quotes being stored in {@code lines}.
     */
    private static ObservableList<String> lines = FXCollections.observableArrayList();

    public static ObservableList<String> getLines() {
        return lines;
    }

    /**
     * Initialise data by retrieving it from resources.
     */
    public static void readFile() {
        try {
            Path relative = Paths.get(Objects.requireNonNull(Quote.class.getClassLoader()
                    .getResource("text/quotes.txt")).toURI());
            lines.addAll(Files.readAllLines(relative));
        } catch (IOException | URISyntaxException error) {
            Logger logger = LogsCenter.getLogger(LogsCenter.class);
            logger.info(error.getMessage());
        }
    }

    public static String getQuote() {
        Random random = new Random();
        int i = random.nextInt(lines.size());
        return lines.get(i);
    }

}
