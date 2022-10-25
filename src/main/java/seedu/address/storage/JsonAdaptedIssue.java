package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.Deadline;
import seedu.address.model.interfaces.HasIntegerIdentifier;
import seedu.address.model.issue.Issue;
import seedu.address.model.issue.IssueId;
import seedu.address.model.issue.Priority;
import seedu.address.model.issue.Status;
import seedu.address.model.issue.Title;
import seedu.address.model.list.NotFoundException;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * Jackson-friendly version of {@link Issue}.
 */
class JsonAdaptedIssue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue's %s field is missing!";

    private final String title;
    private final String priority;
    private final String deadline;
    private final String status;
    private final String issueId;
    private final String project;

    /**
     * Constructs a {@code JsonAdaptedIssue} with the given issue details.
     */
    @JsonCreator
    public JsonAdaptedIssue(@JsonProperty("title") String title,
                            @JsonProperty("priority") String priority,
                             @JsonProperty("deadline") String deadline,
                            @JsonProperty("status") String status,
                            @JsonProperty("issueId") String issueId,
                            @JsonProperty("project") String project) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.project = project;
        this.issueId = issueId;
    }

    /**
     * Converts a given {@code Issue} into this class for Jackson use.
     */
    public JsonAdaptedIssue(Issue source) {
        title = source.getTitle().toString();
        priority = source.getPriority().toString();
        deadline = source.getDeadline().toString();
        status = source.getStatus().toString();
        issueId = source.getIssueId().toString();
        project = source.getProject().getProjectId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted issue object into the model's {@code Issue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted issue.
     */
    public Issue toModelType(AddressBook addressBook) throws IllegalValueException {

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        if (!seedu.address.model.issue.Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }

        if (!Priority.isValidPriorityString(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }

        final Priority modelPriority = Priority.valueOf(priority);

        Deadline modelDeadline;

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }

        if (deadline.isEmpty()) {
            modelDeadline = Deadline.EmptyDeadline.EMPTY_DEADLINE;
        } else {
            if (!Deadline.isValidDeadline(deadline)) {
                throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
            }
            modelDeadline = new Deadline(deadline);
        }

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(Boolean.valueOf(status));

        if (project == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
            Project.class.getSimpleName()));
        }
        final Project modelProject;
        try {
            modelProject = HasIntegerIdentifier.getElementById(
                    addressBook.getProjectList(), Integer.parseInt(project));
        } catch (NotFoundException e) {
            throw new IllegalValueException(ProjectId.MESSAGE_CONSTRAINTS);
        }

        if (issueId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, IssueId.class.getSimpleName()));
        }
        if (!IssueId.isValidIssueId(issueId)) {
            throw new IllegalValueException(IssueId.MESSAGE_CONSTRAINTS);
        }
        final IssueId modelIssueId = new IssueId(Integer.parseInt(issueId));

        assert modelIssueId.getIdInt() >= 0 : "Issue ID should be positive";

        return new Issue(modelTitle, modelDeadline, modelPriority, modelStatus, modelProject, modelIssueId);
    }

}
