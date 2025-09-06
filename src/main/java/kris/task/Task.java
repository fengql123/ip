package kris.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public abstract TaskType getTaskType();

    @Override
    public String toString() {
        return getTaskType() + "[" + getStatusIcon() + "] " + description;
    }
    
    public abstract String toFileString();
}