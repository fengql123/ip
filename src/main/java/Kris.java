import java.util.Scanner;

public class Kris {
    private static final String DIVIDER = "____________________________________________________________";
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        showWelcome();
        
        String input;
        
        while (true) {
            input = scanner.nextLine();
            
            try {
                Command command = Command.fromString(input);
                
                switch (command) {
                    case BYE:
                        showGoodbye();
                        scanner.close();
                        return;
                    case LIST:
                        handleListCommand();
                        break;
                    case MARK:
                        handleMarkCommand(input);
                        break;
                    case UNMARK:
                        handleUnmarkCommand(input);
                        break;
                    case TODO:
                        handleTodoCommand(input);
                        break;
                    case DEADLINE:
                        handleDeadlineCommand(input);
                        break;
                    case EVENT:
                        handleEventCommand(input);
                        break;
                    case DELETE:
                        handleDeleteCommand(input);
                        break;
                    default:
                        throw new InvalidCommandException(input);
                }
            } catch (EmptyDescriptionException e) {
                System.out.println(DIVIDER);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Make sure to include a description after the command!");
                System.out.println(DIVIDER);
            } catch (MissingParameterException e) {
                System.out.println(DIVIDER);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Check the format: deadline [task] /by [time], event [task] /from [start] /to [end]");
                System.out.println(DIVIDER);
            } catch (InvalidTaskNumberException e) {
                System.out.println(DIVIDER);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Use 'list' to see your tasks and their numbers!");
                System.out.println(DIVIDER);
            } catch (InvalidDateFormatException e) {
                System.out.println(DIVIDER);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(DIVIDER);
            } catch (InvalidCommandException e) {
                System.out.println(DIVIDER);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Try these commands:");
                System.out.println(" - todo [description]");
                System.out.println(" - deadline [description] /by [time]");
                System.out.println(" - event [description] /from [start] /to [end]");
                System.out.println(" - list, mark [number], unmark [number], delete [number], bye");
                System.out.println(DIVIDER);
            }
        }
    }
    
    private static void showWelcome() {
        String logo = " _  __     _     \n"
                + "| |/ /_ __(_)___ \n"
                + "| ' /| '__| / __|\n"
                + "| . \\| |  | \\__ \\\n"
                + "|_|\\_\\_|  |_|___/\n";
        System.out.println("Hello from\n" + logo);
        
        System.out.println(DIVIDER);
        System.out.println(" Yo yo yo! I'm Kris, your rap bot extraordinaire!");
        System.out.println(" Drop me some beats... I mean commands! What can I do for you?");
        System.out.println(DIVIDER);
    }
    
    private static void showGoodbye() {
        System.out.println(DIVIDER);
        System.out.println(" Peace out! Keep it real, catch you on the flip side!");
        System.out.println(" Hope to see you again soon, my friend!");
        System.out.println(DIVIDER);
    }
    
    private static void handleListCommand() {
        System.out.println(DIVIDER);
        if (taskManager.isEmpty()) {
            System.out.println(" Yo! Your task list is empty, time to get busy!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskManager.size(); i++) {
                System.out.println(" " + (i + 1) + "." + taskManager.getTask(i));
            }
        }
        System.out.println(DIVIDER);
    }
    
    private static void showTaskAdded(Task task) {
        System.out.println(DIVIDER);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskManager.size() + " tasks in the list.");
        System.out.println(DIVIDER);
    }
    
    private static void showTaskMarked(Task task) {
        System.out.println(DIVIDER);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        System.out.println(DIVIDER);
    }
    
    private static void showTaskUnmarked(Task task) {
        System.out.println(DIVIDER);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println(DIVIDER);
    }
    
    private static void showTaskDeleted(Task task) {
        System.out.println(DIVIDER);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskManager.size() + " tasks in the list.");
        System.out.println(DIVIDER);
    }
    
    private static void handleMarkCommand(String input) throws MissingParameterException, InvalidTaskNumberException {
        if (input.trim().equals("mark") || input.substring(4).trim().isEmpty()) {
            throw new MissingParameterException("mark", "task number");
        }
        try {
            int index = Integer.parseInt(input.substring(5)) - 1;
            if (taskManager.isValidIndex(index)) {
                taskManager.markTask(index, true);
                showTaskMarked(taskManager.getTask(index));
            } else {
                throw new InvalidTaskNumberException(String.valueOf(index + 1), taskManager.size());
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(input.substring(5));
        }
    }
    
    private static void handleUnmarkCommand(String input) throws MissingParameterException, InvalidTaskNumberException {
        if (input.trim().equals("unmark") || input.substring(6).trim().isEmpty()) {
            throw new MissingParameterException("unmark", "task number");
        }
        try {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (taskManager.isValidIndex(index)) {
                taskManager.markTask(index, false);
                showTaskUnmarked(taskManager.getTask(index));
            } else {
                throw new InvalidTaskNumberException(String.valueOf(index + 1), taskManager.size());
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(input.substring(7));
        }
    }
    
    private static void handleTodoCommand(String input) throws EmptyDescriptionException {
        if (input.trim().equals("todo") || input.substring(4).trim().isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        String description = input.substring(5);
        Task newTask = new Todo(description);
        taskManager.addTask(newTask);
        showTaskAdded(newTask);
    }
    
    private static void handleDeadlineCommand(String input) throws EmptyDescriptionException, InvalidDateFormatException {
        if (input.trim().equals("deadline") || input.substring(8).trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        int byIndex = input.indexOf("/by ");
        if (byIndex == -1) {
            System.out.println(DIVIDER);
            System.out.println(" Yo! Use '/by' to specify the deadline!");
            System.out.println(DIVIDER);
        } else {
            String description = input.substring(9, byIndex).trim();
            String by = input.substring(byIndex + 4).trim();
            
            // Validate date format
            if (!DateParser.isValidDateTime(by)) {
                throw new InvalidDateFormatException(by);
            }
            
            Task newTask = new Deadline(description, by);
            taskManager.addTask(newTask);
            showTaskAdded(newTask);
        }
    }
    
    private static void handleEventCommand(String input) throws InvalidDateFormatException {
        int fromIndex = input.indexOf("/from ");
        int toIndex = input.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1) {
            System.out.println(DIVIDER);
            System.out.println(" Yo! Use '/from' and '/to' to specify the event time!");
            System.out.println(DIVIDER);
        } else {
            String description = input.substring(6, fromIndex).trim();
            String from = input.substring(fromIndex + 6, toIndex).trim();
            String to = input.substring(toIndex + 4).trim();
            
            // Validate date formats
            if (!DateParser.isValidDateTime(from)) {
                throw new InvalidDateFormatException(from);
            }
            if (!DateParser.isValidDateTime(to)) {
                throw new InvalidDateFormatException(to);
            }
            
            Task newTask = new Event(description, from, to);
            taskManager.addTask(newTask);
            showTaskAdded(newTask);
        }
    }
    
    private static void handleDeleteCommand(String input) throws MissingParameterException, InvalidTaskNumberException {
        if (input.trim().equals("delete") || input.substring(6).trim().isEmpty()) {
            throw new MissingParameterException("delete", "task number");
        }
        try {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (taskManager.isValidIndex(index)) {
                Task deletedTask = taskManager.removeTask(index);
                showTaskDeleted(deletedTask);
            } else {
                throw new InvalidTaskNumberException(String.valueOf(index + 1), taskManager.size());
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(input.substring(7));
        }
    }
}