package seedu.address.model.issue;

import seedu.address.model.project.Project;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Issue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Issue {

    // Components of an issue
    private Id id;
    private Description description;
    private Deadline deadline;
    private Priority priority;
    private Status status;
    private Project project;

    /**
     * Description field must be present and not null, but all other fields are optional.
     */
    public Issue(Description description, Deadline deadline, Priority priority, Status status, Project project) {
        requireAllNonNull(description, deadline, priority, status, project);
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.project = project;
    }


    public Id getId() {
        return this.id;
    }

    public Description getDescription() {
        return this.description;
    }

    public Deadline getDeadline() {
        return this.deadline;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Project getProject() {
        return this.project;
    }

    /**
     * Returns true if both issues have the same description.
     * This defines a weaker notion of equality between two issues.
     */
    public boolean isSameIssue(Issue otherIssue) {
        if (otherIssue == this) {
            return true;
        }

        return otherIssue != null
                && otherIssue.getDescription().equals(getDescription());
    }

    public Status getStatus() {
        return this.status;
    }

    /**
     * Returns true if both projects have the same identity and data fields.
     * This defines a stronger notion of equality between two projects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Issue)) {
            return false;
        }

        Issue otherIssue = (Issue) other;
        return otherIssue.getDescription().equals(getDescription())
                && otherIssue.getProject().equals(getProject())
                && otherIssue.getDeadline().equals(getDeadline())
                && otherIssue.getStatus().equals(getStatus())
                && otherIssue.getPriority().equals(getPriority());
    }
}
