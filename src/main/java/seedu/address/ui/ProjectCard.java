package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.project.Project;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class ProjectCard extends UiPart<Region> {

    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Hyperlink repository;
    @FXML
    private Label deadline;
    @FXML
    private Label client;
    @FXML
    private Label issueCount;

    /**
     * Creates a {@code ProjectCard} with the given {@code Project} and index to display.
     */
    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        name.setText(project.getProjectName().toString() + " " + project.getProjectId().uiRepresentation());
        repository.setText(project.getRepository().isEmpty() ? "No Repository Set"
                : project.getRepository().getRepositoryUrl());
        deadline.setText(project.getDeadline().isEmpty() ? "No Deadline Set"
                : project.getDeadline().getFormattedDeadline());
        client.setText(project.getClient().isEmpty()
                ? "No Client Set"
                : "In Charge: " + project.getClient().uiRepresentation());
        issueCount.setText(project.getIssueList().size() + " issues (" +
                project.getCompletedIssueCount() + " complete, "
                + project.getIncompleteIssueCount() + " incomplete)");

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectCard)) {
            return false;
        }

        // state check
        ProjectCard card = (ProjectCard) other;
        return id.getText().equals(card.id.getText())
                && project.equals(card.project);
    }
}

