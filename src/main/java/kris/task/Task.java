package kris.task;

/**
 * Abstract base class representing a task in the task management system.
 * All task types inherit from this class and implement task-specific behavior.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a task with the given description.
     * Task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the type of this task.
     *
     * @return TaskType representing the specific type of this task.
     */
    public abstract TaskType getTaskType();

    /**
     * Gets the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return true if task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return getTaskType() + "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts this task to a string format suitable for file storage.
     *
     * @return String representation of this task for file persistence.
     */
    public abstract String toFileString();
}
