import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TaskManager {
    private static final String DATA_DIR = "./data";
    private static final String DATA_FILE = "./data/duke.txt";
    private ArrayList<Task> tasks;
    
    public TaskManager() {
        this.tasks = new ArrayList<>();
        loadTasks();
    }
    
    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }
    
    public Task removeTask(int index) {
        Task removedTask = tasks.remove(index);
        saveTasks();
        return removedTask;
    }
    
    public void markTask(int index, boolean isDone) {
        if (isDone) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
        saveTasks();
    }
    
    public Task getTask(int index) {
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
    
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    
    private void saveTasks() {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            FileWriter writer = new FileWriter(DATA_FILE);
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(" Warning: Could not save tasks to file.");
        }
    }
    
    private void loadTasks() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return;
            }
            
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            return;
        }
    }
    
    private Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;
        
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        
        Task task = null;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    task = new Deadline(description, parts[3]);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    task = new Event(description, parts[3], parts[4]);
                }
                break;
        }
        
        if (task != null && isDone) {
            task.markAsDone();
        }
        
        return task;
    }
}