package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import nasa.model.Model;
import nasa.model.activity.Activity;

/**
 * Lists all modules and their activity lists to the user.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_SUCCESS = "These are the statistics.";

    private final Predicate<Activity> activityPredicate;

    public StatisticsCommand(Predicate<Activity> activityPredicate) {
        this.activityPredicate = activityPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredActivityList(activityPredicate);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
