package seedu.address.model.issue;

import seedu.address.model.Deadline;
import seedu.address.model.Model;
import seedu.address.model.project.ProjectId;

import java.util.function.Function;

/**
 * This class represents a partial initialisation of issue (without access to model).
 * The reason why this exists is to separate Parser classes, and to ensure they are not dependent on
 * Addressbook/Model classes.
 * <p>
 * This is inspired from a functional programming appraoch, where we store intermediate data by nesting functions.
 */
public class IssueWithoutModel implements Function<Model, Issue> {

    private final Description description;
    private final Deadline deadline;
    private final Status status;
    private final Priority priority;
    private final ProjectId projectId;

    public IssueWithoutModel(Description description, Deadline deadline,
                             Priority priority, Status status, ProjectId projectId) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.projectId = projectId;

    }

    @Override
    public Issue apply(Model model) {
        return new Issue(description,
                deadline,
                priority,
                status,
                model.getProjectById(projectId.getIdInt()),
                new IssueId(model.generateIssueId())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IssueWithoutModel)) {
            return false;
        }
        IssueWithoutModel i = (IssueWithoutModel) o;
        return i == this || (
                this.description.equals(i.description) &&
                        this.deadline.equals(i.deadline) &&
                        this.priority.equals(i.priority) &&
                        this.status.equals(i.status) &&
                        this.projectId.equals(i.projectId));
    }
}
