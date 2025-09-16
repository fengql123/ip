package kris;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kris.task.Deadline;
import kris.task.Task;
import kris.task.TaskType;
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

    /**
     * Sorts tasks by type, then by completion status, then alphabetically by description.
     * Order: Deadlines first, then Events, then Todos.
     * Within each type: incomplete tasks first, then completed tasks.
     */
    public void sortByDefault() {
        tasks.sort(Comparator
                .comparing((Task task) -> task.getTaskType().ordinal())
                .thenComparing(Task::isDone)
                .thenComparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Sorts deadlines chronologically (earliest first), with other tasks at the end.
     * Non-deadline tasks are sorted alphabetically by description.
     * Tasks with unparseable dates are placed at the end.
     */
    public void sortByDeadline() {
        tasks.sort((task1, task2) -> {
            boolean isTask1Deadline = task1.getTaskType() == TaskType.DEADLINE;
            boolean isTask2Deadline = task2.getTaskType() == TaskType.DEADLINE;
            
            if (isTask1Deadline && isTask2Deadline) {
                LocalDateTime deadline1 = ((Deadline) task1).getDeadline();
                LocalDateTime deadline2 = ((Deadline) task2).getDeadline();
                
                if (deadline1 == null && deadline2 == null) {
                    return task1.getDescription().compareToIgnoreCase(task2.getDescription());
                }
                if (deadline1 == null) return 1;
                if (deadline2 == null) return -1;
                
                return deadline1.compareTo(deadline2);
            } else if (isTask1Deadline) {
                return -1;
            } else if (isTask2Deadline) {
                return 1;
            } else {
                return task1.getDescription().compareToIgnoreCase(task2.getDescription());
            }
        });
    }

    /**
     * Sorts tasks alphabetically by description (case-insensitive).
     */
    public void sortByDescription() {
        tasks.sort(Comparator.comparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Sorts tasks by completion status (incomplete tasks first).
     * Within each group, tasks are sorted alphabetically by description.
     */
    public void sortByStatus() {
        tasks.sort(Comparator
                .comparing(Task::isDone)
                .thenComparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER));
    }
}
