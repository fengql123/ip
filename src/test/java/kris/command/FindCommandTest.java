package kris.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.task.Todo;
import kris.task.Deadline;
import kris.task.Event;
import kris.exception.KrisException;
import kris.exception.EmptyDescriptionException;

public class FindCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("test_data.txt");
        
        // Redirect System.out to capture output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // Add test tasks
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("buy groceries"));
        tasks.add(new Deadline("return book", "2/12/2019 1800"));
        tasks.add(new Event("book club meeting", "3/12/2019 1400", "3/12/2019 1600"));
    }

    @Test
    public void execute_findMatchingTasks_displaysCorrectResults() throws KrisException {
        FindCommand findCommand = new FindCommand("find book");
        
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("return book"));
        assertTrue(output.contains("book club meeting"));
        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
        assertTrue(output.contains("3."));
    }

    @Test
    public void execute_findCaseInsensitive_displaysCorrectResults() throws KrisException {
        FindCommand findCommand = new FindCommand("find BOOK");
        
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("return book"));
        assertTrue(output.contains("book club meeting"));
    }

    @Test
    public void execute_findSpecificKeyword_displaysOnlyMatchingTasks() throws KrisException {
        FindCommand findCommand = new FindCommand("find groceries");
        
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("buy groceries"));
        assertFalse(output.contains("read book"));
        assertFalse(output.contains("return book"));
        assertTrue(output.contains("1."));
        assertFalse(output.contains("2."));
    }

    @Test
    public void execute_findNoMatches_displaysNoMatchesMessage() throws KrisException {
        FindCommand findCommand = new FindCommand("find xyz");
        
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("No matching tasks found in your list!"));
        assertFalse(output.contains("Here are the matching tasks in your list:"));
    }

    @Test
    public void execute_findEmptyTaskList_displaysNoMatchesMessage() throws KrisException {
        TaskList emptyTasks = new TaskList();
        FindCommand findCommand = new FindCommand("find book");
        
        findCommand.execute(emptyTasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("No matching tasks found in your list!"));
    }

    @Test
    public void execute_findEmptyKeyword_throwsEmptyDescriptionException() {
        FindCommand findCommand = new FindCommand("find");
        
        assertThrows(EmptyDescriptionException.class, () -> {
            findCommand.execute(tasks, ui, storage);
        });
    }

    @Test
    public void execute_findOnlySpaces_throwsEmptyDescriptionException() {
        FindCommand findCommand = new FindCommand("find   ");
        
        assertThrows(EmptyDescriptionException.class, () -> {
            findCommand.execute(tasks, ui, storage);
        });
    }

    @Test
    public void execute_findPartialMatch_displaysCorrectResults() throws KrisException {
        FindCommand findCommand = new FindCommand("find read");
        
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("read book"));
        assertFalse(output.contains("buy groceries"));
        assertFalse(output.contains("return book"));
    }

    @Test
    public void isExit_returnsFalse() {
        FindCommand findCommand = new FindCommand("find test");
        assertFalse(findCommand.isExit());
    }
}