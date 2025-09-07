package kris.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {
    
    @Test
    public void constructor_validDescription_success() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.description);
        assertFalse(todo.isDone);
    }
    
    @Test
    public void getTaskType_returnsTodo() {
        Todo todo = new Todo("read book");
        assertEquals(TaskType.TODO, todo.getTaskType());
    }
    
    @Test
    public void toString_notDone_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }
    
    @Test
    public void toString_done_correctFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }
    
    @Test
    public void toFileString_notDone_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileString());
    }
    
    @Test
    public void toFileString_done_correctFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("T | 1 | read book", todo.toFileString());
    }
    
    @Test
    public void markAsDone_changesStatus() {
        Todo todo = new Todo("read book");
        assertFalse(todo.isDone);
        todo.markAsDone();
        assertTrue(todo.isDone);
        assertEquals("X", todo.getStatusIcon());
    }
    
    @Test
    public void markAsNotDone_changesStatus() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertTrue(todo.isDone);
        todo.markAsNotDone();
        assertFalse(todo.isDone);
        assertEquals(" ", todo.getStatusIcon());
    }
}