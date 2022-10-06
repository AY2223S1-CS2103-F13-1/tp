package seedu.address.logic.commands.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * TODO: This class will probably expand as there are more commands added,
 * and inheritance/polymorphism becomes a bigger deal as the project grows.
 */
public abstract class EditCommand extends Command {
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
