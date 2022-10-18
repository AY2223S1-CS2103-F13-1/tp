package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.Name;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientEmail;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.ClientPhone;
import seedu.address.model.project.Project;
import seedu.address.model.tag.exceptions.IllegalValueException;


/**
 * Jackson-friendly version of {@link Client}.
 */
class JsonAdaptedClient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Client's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String clientId;

    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedClient} with the given client details.
     */
    @JsonCreator
    public JsonAdaptedClient(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("clientId") String clientId,
                             @JsonProperty("projects") List<JsonAdaptedProject> projects) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.clientId = clientId;
        if (projects != null) {
            this.projects.addAll(projects);
        }
    }

    /**
     * Converts a given {@code Client} into this class for Jackson use.
     */
    public JsonAdaptedClient(Client source) {
        name = source.getClientName().toString();
        phone = source.getClientPhone().toString();
        email = source.getClientEmail().toString();
        clientId = source.getClientId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted client object into the model's {@code Client} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted client.
     */
    public Client toModelType() throws IllegalValueException {
        final List<Project> clientProjects = new ArrayList<>();

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (name.isEmpty()) {
            return Client.EmptyClient.EMPTY_CLIENT;
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        ClientPhone modelPhone;

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClientPhone.class.getSimpleName()));
        }

        if (phone.isEmpty()) {
            modelPhone = ClientPhone.EmptyClientPhone.EMPTY_PHONE;
        } else {
            if (!ClientPhone.isValidClientPhone(phone)) {
                throw new IllegalValueException(ClientPhone.MESSAGE_CONSTRAINTS);
            }
            modelPhone = new ClientPhone(phone);
        }

        ClientEmail modelEmail;

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClientEmail.class.getSimpleName()));
        }

        if (email.isEmpty()) {
            modelEmail = ClientEmail.EmptyEmail.EMPTY_EMAIL;
        } else {
            if (!ClientEmail.isValidEmail(email)) {
                throw new IllegalValueException(ClientEmail.MESSAGE_CONSTRAINTS);
            }
            modelEmail = new ClientEmail(email);
        }

        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClientId.class.getSimpleName()));
        }

        if (!ClientId.isValidClientId(clientId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }

        final ClientId modelClientId = new ClientId(Integer.parseInt(clientId));

        assert modelClientId.getIdInt() >= 0 : "Client ID should be positive";

        return new Client(modelName, modelPhone, modelEmail, clientProjects, modelClientId);
    }

}
