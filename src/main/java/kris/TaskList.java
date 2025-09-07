package kris;

import java.util.ArrayList;
import java.util.List;
import kris.task.Task;
import kris.exception.InvalidTaskNumberException;

/**
 * Manages a collection of tasks with operations for adding, removing, and modifying tasks.
 * Provides safe indexed access and validation for task operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks List of tasks to initialize the TaskList with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task Task to add to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Zero-based index of the task to remove.
     * @return The removed task.
     * @throws InvalidTaskNumberException If the index is invalid.
     */
    public Task remove(int index) throws InvalidTaskNumberException {
        if (!isValidIndex(index)) {
            throw new InvalidTaskNumberException(String.valueOf(index + 1), size());
        }
        return tasks.remove(index);
    }

    public void markTask(int index, boolean isDone) throws InvalidTaskNumberException {
        if (!isValidIndex(index)) {
            throw new InvalidTaskNumberException(String.valueOf(index + 1), size());
        }
        if (isDone) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
    }

    public Task get(int index) throws InvalidTaskNumberException {
        if (!isValidIndex(index)) {
            throw new InvalidTaskNumberException(String.valueOf(index + 1), size());
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
