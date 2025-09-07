package kris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import kris.exception.InvalidDateFormatException;
import kris.exception.InvalidCommandException;
import kris.exception.EmptyDescriptionException;
import kris.exception.KrisException;
import kris.task.Todo;
import kris.task.Deadline;
import kris.task.Event;
import kris.command.Command;
import kris.command.FindCommand;

public class ParserTest {
    
    @Test
    public void parseDeadline_invalidDate_throwsInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> {
            Parser.parseDeadline("deadline submit report /by invalid-date");
        });
    }
    
    @Test
    public void parseEvent_invalidFromDate_throwsInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> {
            Parser.parseEvent("event meeting /from bad-date /to 2019-10-16 1400");
        });
    }
    
    @Test
    public void parseEvent_invalidToDate_throwsInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> {
            Parser.parseEvent("event meeting /from 2019-10-16 1400 /to bad-date");
        });
    }
    
    @Test
    public void parseDeadline_validDate_success() throws Exception {
        Deadline deadline = Parser.parseDeadline("deadline submit report /by 2019-10-15");
        assertEquals("D | 0 | submit report | 2019-10-15", deadline.toFileString());
        assertEquals("[D][ ] submit report (by: Oct 15 2019)", deadline.toString());
    }
    
    @Test
    public void parseEvent_validDates_success() throws Exception {
        Event event = Parser.parseEvent("event meeting /from 2019-10-16 1400 /to 2019-10-16 1600");
        assertEquals("E | 0 | meeting | 2019-10-16 1400 | 2019-10-16 1600", event.toFileString());
        assertEquals("[E][ ] meeting (from: Oct 16 2019 1400hrs to: Oct 16 2019 1600hrs)", event.toString());
    }
    
    @Test
    public void parseTodo_emptyDescription_throwsEmptyDescriptionException() {
        assertThrows(EmptyDescriptionException.class, () -> {
            Parser.parseTodo("todo");
        });
    }
    
    @Test
    public void parseTodo_validDescription_success() throws Exception {
        Todo todo = Parser.parseTodo("todo read book");
        assertEquals("T | 0 | read book", todo.toFileString());
        assertEquals("[T][ ] read book", todo.toString());
    }
    
    @Test
    public void parse_invalidCommand_throwsInvalidCommandException() {
        assertThrows(InvalidCommandException.class, () -> {
            Parser.parse("blah");
        });
    }
    
    @Test
    public void parseDeadline_missingBy_throwsKrisException() {
        assertThrows(KrisException.class, () -> {
            Parser.parseDeadline("deadline submit report");
        });
    }
    
    @Test
    public void parseEvent_missingFromTo_throwsKrisException() {
        assertThrows(KrisException.class, () -> {
            Parser.parseEvent("event meeting");
        });
    }
    
    @Test
    public void parseTaskNumber_validNumber_returnsCorrectNumber() throws Exception {
        int result = Parser.parseTaskNumber("mark 5", "mark");
        assertEquals(5, result);
    }
    
    @Test
    public void parseTaskNumber_invalidNumber_throwsException() {
        assertThrows(Exception.class, () -> {
            Parser.parseTaskNumber("mark abc", "mark");
        });
    }
    
    @Test
    public void parseTaskNumber_emptyNumber_throwsException() {
        assertThrows(Exception.class, () -> {
            Parser.parseTaskNumber("mark", "mark");
        });
    }
    
    @Test
    public void parse_findCommand_returnsFindCommand() throws Exception {
        Command command = Parser.parse("find book");
        assertTrue(command instanceof FindCommand);
    }
    
    @Test
    public void parse_findCommandWithKeyword_returnsFindCommand() throws Exception {
        Command command = Parser.parse("find test keyword");
        assertTrue(command instanceof FindCommand);
        assertFalse(command.isExit());
    }
    
    @Test
    public void parse_findEmptyKeyword_returnsFindCommand() throws Exception {
        // Parser should return FindCommand, but execution will throw EmptyDescriptionException
        Command command = Parser.parse("find");
        assertTrue(command instanceof FindCommand);
    }
}