package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_ACTIVITY_LISTED_OVERVIEW;
import static nasa.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;
import nasa.model.module.Module;
import nasa.model.module.NameContainsKeywordsPredicate;

/**
 * Finds and lists all activities in NASA whose name contains any of the argument keywords.
 * Represents the command for finding specific activities.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment lab tutorial";

    public static final String MESSAGE_REFRESH = "Screen has been refreshed.";

    private final Predicate predicate;

    public FindCommand() {
        this.predicate = null;
    }

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        if (predicate instanceof NameContainsKeywordsPredicate) {
            model.updateFilteredModuleList(predicate);
            return new CommandResult("SUCCESS MODULE DISPLAYED.");
        } else if (predicate instanceof ActivityContainsKeyWordsPredicate) {
            model.updateFilteredActivityList(predicate);
            return new CommandResult(String.format(MESSAGE_ACTIVITY_LISTED_OVERVIEW,
                    getNumberOfFilteredActivities(model.getFilteredModuleList())));
        } else {
            model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
            return new CommandResult(MESSAGE_REFRESH);
        }
    }

    private int getNumberOfFilteredActivities(ObservableList<Module> moduleList) {
        int numberOfActivities = 0;
        for (Module module : moduleList) {
            numberOfActivities += module.getFilteredActivityList().size();
        }
        return numberOfActivities;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
