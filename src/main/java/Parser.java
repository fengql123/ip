public class Parser {
    
    public static Command parse(String input) throws KrisException {
        input = input.trim();
        
        if (input.equals("bye")) {
            return new ByeCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
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
        } else {
            throw new InvalidCommandException(input);
        }
    }
    
    public static int parseTaskNumber(String input, String commandWord) throws MissingParameterException, InvalidTaskNumberException {
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
    
    public static String parseDescription(String input, String commandWord) throws EmptyDescriptionException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(commandWord);
        }
        return parts[1].trim();
    }
    
    public static Todo parseTodo(String input) throws EmptyDescriptionException {
        String description = parseDescription(input, "todo");
        return new Todo(description);
    }
    
    public static Deadline parseDeadline(String input) throws EmptyDescriptionException, InvalidDateFormatException, KrisException {
        String description = parseDescription(input, "deadline");
        
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
        
        return new Event(description, from, to);
    }
}