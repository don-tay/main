package nasa.model.module;

import java.util.List;
import java.util.function.Predicate;

import nasa.commons.util.StringUtil;

/**
 * Predicate to filter based on activity name.
 */
public class NameContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(module.getModuleCode().moduleCode, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
