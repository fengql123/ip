package kris.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {
    
    @Test
    public void invalidCommandException_correctMessage() {
        InvalidCommandException exception = new InvalidCommandException("blah");
        String message = exception.getMessage();
        assertTrue(message.contains("Yo! I don't know what 'blah' means, my friend!"));
        assertTrue(message.contains("Try these commands:"));
        assertTrue(message.contains("- todo [description]"));
        assertTrue(message.contains("- deadline [description] /by [time]"));
        assertTrue(message.contains("- event [description] /from [start] /to [end]"));
        assertTrue(message.contains("- list, mark [number], unmark [number], delete [number], bye"));
    }
    
    @Test
    public void emptyDescriptionException_todoType_correctMessage() {
        EmptyDescriptionException exception = new EmptyDescriptionException("todo");
        assertEquals("Yo! The description of a todo cannot be empty, my friend!", exception.getMessage());
    }
    
    @Test
    public void emptyDescriptionException_deadlineType_correctMessage() {
        EmptyDescriptionException exception = new EmptyDescriptionException("deadline");
        assertEquals("Yo! The description of a deadline cannot be empty, my friend!", exception.getMessage());
    }
    
    @Test
    public void invalidDateFormatException_correctMessage() {
        InvalidDateFormatException exception = new InvalidDateFormatException("invalid-date");
        String message = exception.getMessage();
        assertTrue(message.contains("Yo! I can't understand the date 'invalid-date'!"));
        assertTrue(message.contains("Try these formats: yyyy-mm-dd"));
        assertTrue(message.contains("d/m/yyyy"));
        assertTrue(message.contains("2/12/2019 1800"));
    }
    
    @Test
    public void invalidTaskNumberException_withTaskCount_correctMessage() {
        InvalidTaskNumberException exception = new InvalidTaskNumberException("5", 3);
        assertEquals("Yo! Task number 5 doesn't exist! You have 3 tasks.", exception.getMessage());
    }
    
    @Test
    public void invalidTaskNumberException_invalidInput_correctMessage() {
        InvalidTaskNumberException exception = new InvalidTaskNumberException("abc");
        assertEquals("Yo! 'abc' ain't a valid number! Use a number like 1, 2, 3...", exception.getMessage());
    }
    
    @Test
    public void missingParameterException_markCommand_correctMessage() {
        MissingParameterException exception = new MissingParameterException("mark", "task number");
        assertEquals("Yo! You gotta tell me which mark task number! Use 'mark [task number]'.", exception.getMessage());
    }
    
    @Test
    public void missingParameterException_unmarkCommand_correctMessage() {
        MissingParameterException exception = new MissingParameterException("unmark", "task number");
        assertEquals("Yo! You gotta tell me which unmark task number! Use 'unmark [task number]'.", exception.getMessage());
    }
    
    @Test
    public void missingParameterException_deleteCommand_correctMessage() {
        MissingParameterException exception = new MissingParameterException("delete", "task number");
        assertEquals("Yo! You gotta tell me which delete task number! Use 'delete [task number]'.", exception.getMessage());
    }
    
    @Test
    public void krisException_basicMessage() {
        KrisException exception = new KrisException("Test message");
        assertEquals("Test message", exception.getMessage());
    }
}