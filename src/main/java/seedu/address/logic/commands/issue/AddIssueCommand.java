package seedu.address.logic.commands.issue;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.IssueCliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.IssueCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.IssueCliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.IssueCliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ISSUES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.issue.Issue;
import seedu.address.ui.Ui;

import java.util.function.Function;

/**
 * Command to add issue
 */
public class AddIssueCommand extends IssueCommand {

    public static final String COMMAND_FLAG = "-a";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an issue to the address book. "
            + "Parameters: "
            + PREFIX_PROJECT_ID + "PROJECT_ID "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_PRIORITY + "PRIORITY(0, 1, 2) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT_ID + "1 "
            + PREFIX_DESCRIPTION + "to create a person class which stores all relevant person data "
            + PREFIX_DEADLINE + "2022-12-10 "
            + PREFIX_PRIORITY + "0 ";


    public static final String MESSAGE_SUCCESS = "New issue added: %1$s";
    public static final String MESSAGE_DUPLICATE_ISSUE = "This issue already exists in the address book";

    private final Issue toAdd;
    private final Function<Model, Issue> toAddWithoutModel;

//    /**
//     * Creates an AddCommand to add the specified {@code Issue}
//     */
//    public AddIssueCommand(Issue issue) {
//        requireNonNull(issue);
//        toAdd = issue;
//    }

    /**
     * Creates an AddCommand to add the specified {@code Issue}
     */
    public AddIssueCommand(Function<Model, Issue> issueWithoutModel) {
        requireNonNull(issueWithoutModel);
        toAddWithoutModel = issueWithoutModel;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);

        Issue toAdd = toAddWithoutModel.apply(model);

        if (model.hasIssue(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ISSUE);
        }

        ui.showIssues();
        model.updateFilteredIssueList(PREDICATE_SHOW_ALL_ISSUES);


        model.addIssue(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddIssueCommand
                && toAdd.equals(((AddIssueCommand) other).toAdd));
    }
}

