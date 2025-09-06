package kris.task;

import java.time.LocalDateTime;
import kris.util.DateParser;

public class Deadline extends Task {

    protected LocalDateTime by;
    protected String originalByString;

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