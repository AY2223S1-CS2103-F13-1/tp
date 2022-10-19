package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.interfaces.HasIntegerIdentifier;
import seedu.address.model.issue.Issue;
import seedu.address.model.list.UniqueEntityList;
import seedu.address.model.project.Project;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed
 *
 * This is a SINGLETON CLASS.
 * Static methods are present to retrieve, and create a new instance of the class.
 * When creating a new instance, the previous instance is destroyed.
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEntityList<Client> clients;
    private final UniqueEntityList<Project> projects;
    private final UniqueEntityList<Issue> issues;

    /**
     * Creates an empty addressbook
     */
    public AddressBook() {
        clients = new UniqueEntityList<>();
        projects = new UniqueEntityList<>();
        issues = new UniqueEntityList<>();
    }

    /**
     * Creates an AddressBook.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the project list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setList(projects);
    }

    /**
     * Replaces the contents of the issue list with {@code issues}.
     * {@code issues} must not contain duplicate issues.
     */
    public void setIssues(List<Issue> issues) {
        this.issues.setList(issues);
    }

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setList(clients);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setIssues(newData.getIssueList());
        setProjects(newData.getProjectList());
        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Sorts client list by <code>ClientId</code>
     *
     */
    public void sortClientListById() {
        clients.sortById();
    }

    /**
     * Returns true if a project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.containsByName(project);
    }

    /**
     * Returns true if an issue with the same identity as {@code issue} exists in the address book.
     */
    public boolean hasIssue(Issue issue) {
        requireNonNull(issue);
        return issues.containsByName(issue);
    }

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.containsByName(client);
    }

    /**
     * Returns true if a project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasProjectId(int id) {
        return projects.containsId(id);
    }

    /**
     * Returns true if an issue with the same identity as {@code issue} exists in the address book.
     */
    public boolean hasIssueId(int id) {
        return issues.containsId(id);
    }

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClientId(int id) {
        return clients.containsId(id);
    }

    /**
     * Adds a project to the address book.
     * The project must not already exist in the address book.
     */
    public void addProject(Project p) {
        projects.add(p);
    }

    /**
     * Adds an issue to the address book.
     * The issue must not already exist in the address book.
     */
    public void addIssue(Issue i) {
        issues.add(i);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client c) {
        clients.add(c);
    }

    /**
     * Replaces the given project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the address book.
     * The project identity of {@code editedProject} must not be the same as another
     * existing project in the address book.
     */
    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);

        projects.setItem(target, editedProject);
    }

    /**
     * Replaces the given issue {@code target} in the list with {@code editedIssue}.
     * {@code target} must exist in the address book.
     * The issue identity of {@code editedIssue} must not be the same as another
     * existing issue in the address book.
     */
    public void setIssue(Issue target, Issue editedIssue) {
        requireNonNull(editedIssue);

        issues.setItem(target, editedIssue);
    }

    /**
     * Replaces the given issue {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The issue identity of {@code editedClient} must not be the same as another
     * existing client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);

        clients.setItem(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProject(Project key) {
        projects.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeIssue(Issue key) {
        issues.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return projects.asUnmodifiableObservableList().size() + " projects\n"
                + issues.asUnmodifiableObservableList().size() + " issues\n"
                + clients.asUnmodifiableObservableList().size() + " clients\n";
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Issue> getIssueList() {
        return issues.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public Project getProjectById(int id) {
        return HasIntegerIdentifier.getElementById(projects, id);
    }

    @Override
    public Issue getIssueById(int id) {
        return HasIntegerIdentifier.getElementById(issues, id);
    }

    @Override
    public Client getClientById(int id) {
        return HasIntegerIdentifier.getElementById(clients, id);
    }

    @Override
    public int generateClientId() {
        return HasIntegerIdentifier.generateNextID(clients);
    }

    @Override
    public int generateIssueId() {
        return HasIntegerIdentifier.generateNextID(issues);
    }

    @Override
    public int generateProjectId() {
        return HasIntegerIdentifier.generateNextID(projects);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && clients.equals(((AddressBook) other).clients));
    }

    @Override
    public int hashCode() {
        // TODO: Check for appropriate hashcode
        return clients.hashCode();
    }


}
