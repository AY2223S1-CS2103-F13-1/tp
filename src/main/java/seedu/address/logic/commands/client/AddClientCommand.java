package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ClientCliSyntax.PREFIX_CLIENT_EMAIL;
import static seedu.address.logic.parser.ClientCliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.ClientCliSyntax.PREFIX_CLIENT_PHONE;
import static seedu.address.logic.parser.ClientCliSyntax.PREFIX_PROJECT_ID;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientWithoutModel;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.ui.Ui;


/**
 * Adds a client to the address book.
 */
public class AddClientCommand extends ClientCommand {

    public static final String COMMAND_FLAG = "-a";

    public static final String MESSAGE_ADD_CLIENT_USAGE = COMMAND_WORD + " " + COMMAND_FLAG
            + ": Adds a client to the address book. "
            + "Parameters: "
            + PREFIX_CLIENT_NAME + "NAME "
            + PREFIX_CLIENT_PHONE + "PHONE "
            + PREFIX_CLIENT_EMAIL + "EMAIL "
            + PREFIX_PROJECT_ID + "PROJECT ID: "
            + "Example: " + COMMAND_WORD + " "
            + COMMAND_FLAG + " "
            + PREFIX_CLIENT_NAME + "John Doe "
            + PREFIX_CLIENT_PHONE + "98765432 "
            + PREFIX_CLIENT_EMAIL + "johnd@example.com "
            + PREFIX_PROJECT_ID + "1";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    private static final String MESSAGE_CLIENT_ALREADY_PRESENT = "This project already has a client";
    private static final String MESSAGE_PROJECT_NOT_FOUND = "This project id does not exist in the address book";

    private final ClientWithoutModel toAddClientWithoutModel;
    private final ProjectId projectId;

    /**
     * Creates an AddCommand to add the specified {@code Client}
     * @param clientWithoutModel
     */
    public AddClientCommand(ClientWithoutModel clientWithoutModel, ProjectId pid) {
        requireNonNull(clientWithoutModel);
        toAddClientWithoutModel = clientWithoutModel;
        projectId = pid;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);

        if (!model.hasProjectId(projectId.getIdInt())) {
            throw new CommandException(MESSAGE_PROJECT_NOT_FOUND);
        }

        Client toAddClient = toAddClientWithoutModel.apply(model);

        Project toModifyProject = model.getProjectById(projectId.getIdInt());

        if (!toModifyProject.getClient().isEmpty()) {
            throw new CommandException(MESSAGE_CLIENT_ALREADY_PRESENT);
        }

        toModifyProject.setClient(toAddClient);
        toAddClient.addProjects(toModifyProject);

        model.addClient(toAddClient);

        ui.showClients();
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddClient));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddClientCommand)) {
            return false;
        }

        return this.toAddClientWithoutModel.equals(((AddClientCommand) other).toAddClientWithoutModel);
    }
}
