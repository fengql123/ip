package kris.task;

import java.time.LocalDateTime;
import kris.util.DateParser;

/**
 * Represents a task with a deadline.
 * Contains a description and a deadline date/time when the task should be completed.
 */
public class Deadline extends Task {

    protected LocalDateTime by;
    protected String originalByString;

    /**
     * Constructs a Deadline task with the specified description and deadline.
     * Attempts to parse the deadline string into a LocalDateTime object.
     *
     * @param description Description of the deadline task.
     * @param by Deadline date/time as a string in various supported formats.
     */
    public Deadline(String description, String by) {
        super(description);
        this.originalByString = by;
        this.by = DateParser.parseDateTime(by);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    @Override
    public String toString() {
        String dateString = (by != null) ? DateParser.formatDateTime(by) : originalByString;
        return getTaskType() + "[" + getStatusIcon() + "] " + description + " (by: " + dateString + ")";
    }
    
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + originalByString;
    }
}