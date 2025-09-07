package kris;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import kris.task.Todo;
import kris.task.Task;
import kris.exception.InvalidTaskNumberException;

public class TaskListTest {
    private TaskList taskList;
    
    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }
    
    @Test
    public void constructor_emptyTaskList_isEmpty() {
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }
    
    @Test
    public void add_task_increasesSize() {
        Todo todo = new Todo("read book");
        taskList.add(todo);
        
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.size());
    }
    
    @Test
    public void get_validIndex_returnsTask() throws InvalidTaskNumberException {
        Todo todo = new Todo("read book");
        taskList.add(todo);
        
        Task retrieved = taskList.get(0);
        assertEquals("[T][ ] read book", retrieved.toString());
    }
    
    @Test
    public void get_invalidIndex_throwsException() {
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.get(0); // Empty list
        });
        
        taskList.add(new Todo("test"));
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.get(1); // Index out of bounds
        });
        
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.get(-1); // Negative index
        });
    }
    
    @Test
    public void remove_validIndex_removesTaskAndReturnsIt() throws InvalidTaskNumberException {
        Todo todo1 = new Todo("task 1");
        Todo todo2 = new Todo("task 2");
        taskList.add(todo1);
        taskList.add(todo2);
        
        assertEquals(2, taskList.size());
        
        Task removed = taskList.remove(0);
        assertEquals("[T][ ] task 1", removed.toString());
        assertEquals(1, taskList.size());
        
        // Check that remaining task shifted
        Task remaining = taskList.get(0);
        assertEquals("[T][ ] task 2", remaining.toString());
    }
    
    @Test
    public void remove_invalidIndex_throwsException() {
        taskList.add(new Todo("test"));
        
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.remove(1); // Index out of bounds
        });
        
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.remove(-1); // Negative index
        });
    }
    
    @Test
    public void markTask_validIndex_changesTaskStatus() throws InvalidTaskNumberException {
        Todo todo = new Todo("read book");
        taskList.add(todo);
        
        // Initially not done
        Task task = taskList.get(0);
        assertEquals("[T][ ] read book", task.toString());
        
        // Mark as done
        taskList.markTask(0, true);
        task = taskList.get(0);
        assertEquals("[T][X] read book", task.toString());
        
        // Mark as not done
        taskList.markTask(0, false);
        task = taskList.get(0);
        assertEquals("[T][ ] read book", task.toString());
    }
    
    @Test
    public void markTask_invalidIndex_throwsException() {
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.markTask(0, true); // Empty list
        });
        
        taskList.add(new Todo("test"));
        assertThrows(InvalidTaskNumberException.class, () -> {
            taskList.markTask(1, true); // Index out of bounds
        });
    }
    
    @Test
    public void isValidIndex_variousIndices_returnsCorrectBoolean() {
        // Empty list
        assertFalse(taskList.isValidIndex(0));
        assertFalse(taskList.isValidIndex(-1));
        
        taskList.add(new Todo("test"));
        
        // Single item list
        assertTrue(taskList.isValidIndex(0));
        assertFalse(taskList.isValidIndex(1));
        assertFalse(taskList.isValidIndex(-1));
        
        taskList.add(new Todo("test2"));
        
        // Two item list
        assertTrue(taskList.isValidIndex(0));
        assertTrue(taskList.isValidIndex(1));
        assertFalse(taskList.isValidIndex(2));
        assertFalse(taskList.isValidIndex(-1));
    }
}