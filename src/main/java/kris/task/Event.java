package kris.task;

import java.time.LocalDateTime;
import kris.util.DateParser;

/**
 * Represents an event task with a specific time period.
 * Contains a description, start time, and end time for the event.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFromString;
    protected String originalToString;

    /**
     * Constructs an Event task with the specified description, start time, and end time.
     * Attempts to parse both time strings into LocalDateTime objects.
     *
     * @param description Description of the event.
     * @param from Start date/time as a string in various supported formats.
     * @param to End date/time as a string in various supported formats.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.originalFromString = from;
        this.originalToString = to;
        this.from = DateParser.parseDateTime(from);
        this.to = DateParser.parseDateTime(to);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EVENT;
    }

    @Override
    public String toString() {
        String fromString = (from != null) ? DateParser.formatDateTime(from) : originalFromString;
        String toString = (to != null) ? DateParser.formatDateTime(to) : originalToString;
        return getTaskType() + "[" + getStatusIcon() + "] " + description + " (from: " + fromString + " to: " + toString
                + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + originalFromString + " | "
                + originalToString;
    }
}
