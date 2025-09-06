import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;
    
    public Storage(String filePath) {
        this.filePath = filePath;
    }
    
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