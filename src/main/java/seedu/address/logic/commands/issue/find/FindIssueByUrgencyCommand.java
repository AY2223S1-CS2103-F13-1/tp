package seedu.address.logic.commands.issue.find;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.issue.predicates.UrgencyMatchesKeywordsPredicate;
import seedu.address.ui.Ui;

/**
 * Represents a class to find and filter issue list by urgency.
 */
public class FindIssueByUrgencyCommand extends FindIssueCommand {
    private final UrgencyMatchesKeywordsPredicate predicate;

    public FindIssueByUrgencyCommand(UrgencyMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.updateFilteredIssueList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ISSUES_LISTED_OVERVIEW, model.getFilteredIssueList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindIssueByUrgencyCommand // instanceof handles nulls
                && predicate.equals(((FindIssueByUrgencyCommand) other).predicate)); // state check
    }
}
