package nasa.logic.parser;

import static java.util.Objects.requireNonNull;

import nasa.commons.core.index.Index;
import nasa.commons.util.StringUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.SortMethod;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code zeroBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseZeroIndex(String zeroBasedIndex) throws ParseException {
        String trimmedIndex = zeroBasedIndex.trim();
        return Index.fromZeroBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code String date} into a {@code Date}.
     * Checks if the string date is of valid form.
     * @param date of the user input
     * @return Date object created based on user input
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String dateTrimmed = date.trim();
        if (!Date.isValidDate(dateTrimmed)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(dateTrimmed);
    }

    /**
     * Parses {@code String name} into a {@code Name}.
     * Checks if the String name is not empty or does not only consists of whitespaces.
     * @param name of the activity
     * @return Name object of the activity
     * @throws ParseException
     */
    public static Name parseActivityName(String name) throws ParseException {
        requireNonNull(name);
        String nameTrimmed = name.trim();
        if (!Name.isValidName(nameTrimmed)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(nameTrimmed);
    }

    /**
     * Parses {@code String note} into a {@code Note}
     * Checks if the String note isn't empty or doesn't only contain whitespaces.
     * @param note of the activity
     * @return Note object of the activity
     * @throws ParseException
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String noteTrimmed = note.trim();
        if (!Note.isValidNote(noteTrimmed)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(noteTrimmed);
    }

    /**
     * Parses {@code String priority} into a {@code Priority}
     * Checks if the String priority is a correct integer.
     * @param priority of the activity
     * @return Priority object of the activity
     * @throws ParseException
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String priorityTrimmed = priority.trim();
        if (!Priority.isValidPriorityValue(priorityTrimmed)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(priorityTrimmed);
    }

    /**
     * Parses {@code String moduleCode} into a {@code moduleCode}
     * Checks if String moduleCode is correct.
     * @param moduleCode of the module
     * @return ModuleCode object
     * @throws ParseException
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String moduleCodeTrimmed = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(moduleCodeTrimmed)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(moduleCodeTrimmed);
    }

    /**
     * Parses {@code String moduleName} into a {@code moduleName}
     * Checks if String moduleName is correct.
     * @param moduleName of the module
     * @return ModuleName object
     * @throws ParseException
     */
    public static ModuleName parseModuleName(String moduleName) throws ParseException {
        requireNonNull(moduleName);
        String moduleNameTrimmed = moduleName.trim();
        if (!ModuleName.isValidModuleName(moduleNameTrimmed)) {
            throw new ParseException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        return new ModuleName(moduleNameTrimmed);
    }

    /**
     * Parses {@code String date} into a {@code Date}.
     * Checks if the string date is of valid form.
     * @param parameter of the user input
     * @return String object created based on user input
     * @throws ParseException
     */
    public static String parseStatistics(String parameter) throws ParseException {
        requireNonNull(parameter);
        String parameterTrimmed = parameter.trim();
        return parameter;
    }

    /**
     * Parses {@code String sortMethod} into a {@code SortMethod}
     * Checks if String sortMethod is correct.
     * @param sortMethod to be used to sort activity list
     * @return SortMethod object
     * @throws ParseException
     */
    public static SortMethod parseSortMethod(String sortMethod) throws ParseException {
        requireNonNull(sortMethod);
        String sortMethodTrimmed = sortMethod.trim();
        if (!SortMethod.isValidSortMethod(sortMethodTrimmed)) {
            throw new ParseException(SortMethod.MESSAGE_CONSTRAINTS);
        }
        return new SortMethod(sortMethodTrimmed);
    }
}
