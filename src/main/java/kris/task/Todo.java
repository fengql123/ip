package kris.task;

/**
 * Represents a simple todo task with a description.
 * This is the most basic type of task without any time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TODO;
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
