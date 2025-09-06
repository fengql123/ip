package kris;

import java.util.ArrayList;
import java.util.List;
import kris.task.Task;
import kris.exception.InvalidTaskNumberException;

public class TaskList {
    private ArrayList<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    
    public void add(Task task) {
        tasks.add(task);
    }
    
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