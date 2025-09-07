package kris.task;

import java.time.LocalDateTime;
import kris.util.DateParser;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFromString;
    protected String originalToString;

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
