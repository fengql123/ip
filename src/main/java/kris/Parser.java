package kris;

import kris.command.ByeCommand;
import kris.command.ListCommand;
import kris.command.ListCommand.SortType;
import kris.command.MarkCommand;
import kris.command.UnmarkCommand;
import kris.command.TodoCommand;
import kris.command.DeadlineCommand;
import kris.command.EventCommand;
import kris.command.DeleteCommand;
import kris.command.FindCommand;
import kris.command.Command;
import kris.task.Todo;
import kris.task.Deadline;
import kris.task.Event;
import kris.exception.KrisException;
import kris.exception.InvalidCommandException;
import kris.exception.MissingParameterException;
import kris.exception.InvalidTaskNumberException;
import kris.exception.EmptyDescriptionException;
import kris.exception.InvalidDateFormatException;
import kris.util.DateParser;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses user input and converts it into executable commands.
 * Handles all command types and validates input format.
 */
public class Parser {

    /**
     * Parses user input string and returns the corresponding command.
     * Recognizes commands: bye, list, mark, unmark, todo, deadline, event, delete.
     *
     * @param input User input string to parse.
     * @return Command object corresponding to the user input.
     * @throws InvalidCommandException If the command is not recognized.
     */
    public static Command parse(String input) throws KrisException {
        input = input.trim();

        if (input.equals("bye")) {
            return new ByeCommand();
        } else if (input.startsWith("list")) {
            return parseListCommand(input);
        } else if (input.startsWith("mark")) {
            return new MarkCommand(input);
        } else if (input.startsWith("unmark")) {
            return new UnmarkCommand(input);
        } else if (input.startsWith("todo")) {
            return new TodoCommand(input);
        } else if (input.startsWith("deadline")) {
            return new DeadlineCommand(input);
        } else if (input.startsWith("event")) {
            return new EventCommand(input);
        } else if (input.startsWith("delete")) {
            return new DeleteCommand(input);
        } else if (input.startsWith("find")) {
            return new FindCommand(input);
        } else {
            throw new InvalidCommandException(input);
        }
    }

    /**
     * Parses task number from user input for commands that require a task index.
     * Validates that the number is present and is a valid positive integer.
     *
     * @param input User input containing the command and task number.
     * @param commandWord Command word for error message generation.
     * @return Task number as a positive integer.
     * @throws MissingParameterException If no task number is provided.
     * @throws InvalidTaskNumberException If the task number is not a valid positive integer.
     */
    public static int parseTaskNumber(String input, String commandWord)
            throws MissingParameterException, InvalidTaskNumberException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MissingParameterException(commandWord, "task number");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (taskNumber <= 0) {
                throw new InvalidTaskNumberException(parts[1].trim());
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(parts[1].trim());
        }
    }

    /**
     * Parses task description from user input.
     * Validates that a non-empty description is provided.
     *
     * @param input User input containing the command and description.
     * @param commandWord Command word for error message generation.
     * @return Task description string.
     * @throws EmptyDescriptionException If no description is provided or description is empty.
     */
    public static String parseDescription(String input, String commandWord) throws EmptyDescriptionException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(commandWord);
        }
        return parts[1].trim();
    }

    /**
     * Parses a todo command and creates a Todo task.
     * Validates that a description is provided.
     *
     * @param input User input for the todo command.
     * @return Todo task with the specified description.
     * @throws EmptyDescriptionException If no description is provided.
     */
    public static Todo parseTodo(String input) throws EmptyDescriptionException {
        String description = parseDescription(input, "todo");
        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     * Validates description and date format. Supports multiple date formats.
     *
     * @param input User input for the deadline command.
     * @return Deadline task with the specified description and deadline.
     * @throws EmptyDescriptionException If no description is provided.
     * @throws InvalidDateFormatException If the date format is invalid.
     * @throws KrisException If the '/by' keyword is missing.
     */
    public static Deadline parseDeadline(String input)
            throws EmptyDescriptionException, InvalidDateFormatException, KrisException {
        int byIndex = input.indexOf("/by ");
        if (byIndex == -1) {
            throw new KrisException("Yo! Use '/by' to specify the deadline!");
        }

        String taskDescription = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 4).trim();

        if (!DateParser.isValidDateTime(by)) {
            throw new InvalidDateFormatException(by);
        }

        return new Deadline(taskDescription, by);
    }

    /**
     * Parses an event command and creates an Event task.
     * Validates description and date formats for both start and end times.
     * Ensures that the end time is not earlier than the start time.
     *
     * @param input User input for the event command.
     * @return Event task with the specified description, start time, and end time.
     * @throws InvalidDateFormatException If either date format is invalid.
     * @throws KrisException If '/from' or '/to' keywords are missing or end time is before start time.
     */
    public static Event parseEvent(String input) throws InvalidDateFormatException, KrisException {
        int fromIndex = input.indexOf("/from ");
        int toIndex = input.indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw new KrisException("Yo! Use '/from' and '/to' to specify the event time!");
        }

        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 4).trim();

        if (!DateParser.isValidDateTime(from)) {
            throw new InvalidDateFormatException(from);
        }
        if (!DateParser.isValidDateTime(to)) {
            throw new InvalidDateFormatException(to);
        }

        // Validate that 'to' date is not earlier than 'from' date
        java.time.LocalDateTime fromDateTime = DateParser.parseDateTime(from);
        java.time.LocalDateTime toDateTime = DateParser.parseDateTime(to);

        if (toDateTime.isBefore(fromDateTime)) {
            throw new KrisException("Yo! The end time cannot be earlier than the start time!");
        }

        return new Event(description, from, to);
    }

    private static final Map<String, SortType> LIST_SORT_OPTIONS = new HashMap<>();
    
    static {
        LIST_SORT_OPTIONS.put("list", SortType.NONE);
        LIST_SORT_OPTIONS.put("list deadline", SortType.DEADLINE);
        LIST_SORT_OPTIONS.put("list description", SortType.DESCRIPTION);
        LIST_SORT_OPTIONS.put("list status", SortType.STATUS);
        LIST_SORT_OPTIONS.put("list default", SortType.DEFAULT);
    }

    /**
     * Parses a list command and creates a ListCommand with appropriate sorting.
     * Supports flags: deadline, description, status, default.
     * Examples: "list", "list deadline", "list description", "list status", "list default"
     *
     * @param input User input for the list command.
     * @return ListCommand with the specified sorting type.
     * @throws InvalidCommandException If the sort flag is invalid.
     */
    public static ListCommand parseListCommand(String input) throws InvalidCommandException {
        assert input != null : "List command input should not be null";
        
        String trimmedInput = input.trim().toLowerCase();
        SortType sortType = LIST_SORT_OPTIONS.get(trimmedInput);
        
        if (sortType != null) {
            return new ListCommand(sortType);
        } else {
            throw new InvalidCommandException("Unknown list sort option. Use: list [deadline/description/status/default]");
        }
    }
}
