package kris.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    
    @Test
    public void constructor_validDescriptionAndDates_success() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        assertEquals(TaskType.EVENT, event.getTaskType());
        assertFalse(event.isDone);
    }
    
    @Test
    public void getTaskType_returnsEvent() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        assertEquals(TaskType.EVENT, event.getTaskType());
    }
    
    @Test
    public void toString_validDates_correctFormat() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        String result = event.toString();
        assertEquals("[E][ ] meeting (from: Oct 16 2019 1400hrs to: Oct 16 2019 1600hrs)", result);
    }
    
    @Test
    public void toString_done_correctFormat() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        event.markAsDone();
        String result = event.toString();
        assertEquals("[E][X] meeting (from: Oct 16 2019 1400hrs to: Oct 16 2019 1600hrs)", result);
    }
    
    @Test
    public void toFileString_notDone_correctFormat() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        assertEquals("E | 0 | meeting | 2019-10-16 1400 | 2019-10-16 1600", event.toFileString());
    }
    
    @Test
    public void toFileString_done_correctFormat() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        event.markAsDone();
        assertEquals("E | 1 | meeting | 2019-10-16 1400 | 2019-10-16 1600", event.toFileString());
    }
    
    @Test
    public void toString_invalidDates_showsOriginalStrings() {
        Event event = new Event("meeting", "bad-from", "bad-to");
        String result = event.toString();
        assertEquals("[E][ ] meeting (from: bad-from to: bad-to)", result);
    }
    
    @Test
    public void constructor_mixedValidInvalidDates_handledCorrectly() {
        Event event1 = new Event("meeting", "2019-10-16 1400", "bad-to");
        Event event2 = new Event("meeting", "bad-from", "2019-10-16 1600");
        
        String result1 = event1.toString();
        String result2 = event2.toString();
        
        assertTrue(result1.contains("Oct 16 2019 1400hrs"));
        assertTrue(result1.contains("bad-to"));
        assertTrue(result2.contains("bad-from"));
        assertTrue(result2.contains("Oct 16 2019 1600hrs"));
    }
    
    @Test
    public void markAsDone_changesStatus() {
        Event event = new Event("meeting", "2019-10-16 1400", "2019-10-16 1600");
        assertFalse(event.isDone);
        assertEquals(" ", event.getStatusIcon());
        
        event.markAsDone();
        assertTrue(event.isDone);
        assertEquals("X", event.getStatusIcon());
        
        event.markAsNotDone();
        assertFalse(event.isDone);
        assertEquals(" ", event.getStatusIcon());
    }
}