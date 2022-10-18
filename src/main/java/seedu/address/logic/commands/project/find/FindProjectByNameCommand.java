package seedu.address.logic.commands.project.find;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.client.find.FindClientByNameCommand;
import seedu.address.model.Model;
import seedu.address.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.address.ui.Ui;

import static java.util.Objects.requireNonNull;

public class FindProjectByNameCommand extends FindProjectCommand {

    private final NameContainsKeywordsPredicate predicate;

    public FindProjectByNameCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.updateFilteredProjectList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW, model.getFilteredProjectList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProjectByNameCommand // instanceof handles nulls
                && predicate.equals(((FindProjectByNameCommand) other).predicate)); // state check
    }

}
