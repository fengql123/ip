package kris;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import kris.task.Task;
import kris.task.Todo;
import kris.task.Deadline;
import kris.task.Event;
import kris.exception.KrisException;

/**
 * Handles file input and output operations for task persistence.
 * Manages loading tasks from file on startup and saving tasks after changes.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath Path to the file for storing and loading tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * Creates an empty list if the file does not exist.
     * Parses each line in the file to reconstruct task objects.
     *
     * @return List of tasks loaded from the file.
     * @throws KrisException If file reading fails or file format is invalid.
     */
    public List<Task> load() throws KrisException {
        List<Task> tasks = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    tasks.add(parseTaskFromFile(line));
                }
            }
        } catch (IOException e) {
            throw new KrisException("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the task list to the storage file.
     * Creates parent directories if they do not exist.
     * Overwrites the existing file with the current task list.
     *
     * @param tasks List of tasks to save to file.
     * @throws KrisException If file writing fails.
     */
    public void save(List<Task> tasks) throws KrisException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new KrisException("Error saving tasks to file: " + e.getMessage());
        }
    }

    private Task parseTaskFromFile(String line) throws KrisException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new KrisException("Invalid file format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new KrisException("Invalid deadline format");
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new KrisException("Invalid event format");
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new KrisException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
