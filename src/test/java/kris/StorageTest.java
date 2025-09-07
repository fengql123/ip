package kris;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
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

public class StorageTest {
    private static final String TEST_FILE_PATH = "test_data/test_tasks.txt";
    private Storage storage;
    
    @BeforeEach
    public void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        // Clean up any existing test files
        cleanUpTestFiles();
    }
    
    @AfterEach
    public void tearDown() {
        cleanUpTestFiles();
    }
    
    private void cleanUpTestFiles() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
            Files.deleteIfExists(Paths.get("test_data"));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    @Test
    public void load_noDataFile_returnsEmptyList() throws KrisException {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }
    
    @Test
    public void save_tasksList_createsFileCorrectly() throws KrisException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("submit report", "2019-10-15"));
        tasks.add(new Event("meeting", "2019-10-16 1400", "2019-10-16 1600"));
        
        storage.save(tasks);
        
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.getParentFile().exists()); // Directory was created
    }
    
    @Test
    public void saveAndLoad_tasksList_maintainsState() throws KrisException {
        // Create test tasks
        List<Task> originalTasks = new ArrayList<>();
        Todo todo = new Todo("read book");
        todo.markAsDone();
        originalTasks.add(todo);
        originalTasks.add(new Deadline("submit report", "2019-10-15"));
        originalTasks.add(new Event("meeting", "2019-10-16 1400", "2019-10-16 1600"));
        
        // Save tasks
        storage.save(originalTasks);
        
        // Load tasks
        List<Task> loadedTasks = storage.load();
        
        // Verify loaded tasks match original
        assertEquals(3, loadedTasks.size());
        
        // Check todo task - verify through public interface
        Task loadedTodo = loadedTasks.get(0);
        assertTrue(loadedTodo instanceof Todo);
        assertEquals("[T][X] read book", loadedTodo.toString());
        assertEquals("T | 1 | read book", loadedTodo.toFileString());
        
        // Check deadline task
        Task loadedDeadline = loadedTasks.get(1);
        assertTrue(loadedDeadline instanceof Deadline);
        assertEquals("[D][ ] submit report (by: Oct 15 2019)", loadedDeadline.toString());
        assertEquals("D | 0 | submit report | 2019-10-15", loadedDeadline.toFileString());
        
        // Check event task
        Task loadedEvent = loadedTasks.get(2);
        assertTrue(loadedEvent instanceof Event);
        assertEquals("[E][ ] meeting (from: Oct 16 2019 1400hrs to: Oct 16 2019 1600hrs)", loadedEvent.toString());
        assertEquals("E | 0 | meeting | 2019-10-16 1400 | 2019-10-16 1600", loadedEvent.toFileString());
    }
    
    @Test
    public void load_corruptedFile_throwsKrisException() throws IOException {
        // Create a file with invalid format
        File file = new File(TEST_FILE_PATH);
        file.getParentFile().mkdirs();
        Files.write(Paths.get(TEST_FILE_PATH), "invalid | format".getBytes());
        
        assertThrows(KrisException.class, () -> {
            storage.load();
        });
    }
    
    @Test
    public void load_invalidTaskType_throwsKrisException() throws IOException {
        // Create a file with unknown task type
        File file = new File(TEST_FILE_PATH);
        file.getParentFile().mkdirs();
        Files.write(Paths.get(TEST_FILE_PATH), "X | 0 | unknown task type".getBytes());
        
        assertThrows(KrisException.class, () -> {
            storage.load();
        });
    }
    
    @Test
    public void load_incompleteDeadlineFormat_throwsKrisException() throws IOException {
        // Create a file with incomplete deadline format (missing date)
        File file = new File(TEST_FILE_PATH);
        file.getParentFile().mkdirs();
        Files.write(Paths.get(TEST_FILE_PATH), "D | 0 | incomplete deadline".getBytes());
        
        assertThrows(KrisException.class, () -> {
            storage.load();
        });
    }
    
    @Test
    public void load_incompleteEventFormat_throwsKrisException() throws IOException {
        // Create a file with incomplete event format (missing to date)
        File file = new File(TEST_FILE_PATH);
        file.getParentFile().mkdirs();
        Files.write(Paths.get(TEST_FILE_PATH), "E | 0 | incomplete event | 2019-10-16 1400".getBytes());
        
        assertThrows(KrisException.class, () -> {
            storage.load();
        });
    }
}