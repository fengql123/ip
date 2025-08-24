import java.util.ArrayList;
import java.util.Scanner;

public class Kris {
    public static void main(String[] args) {
        String logo = " _  __     _     \n"
                + "| |/ /_ __(_)___ \n"
                + "| ' /| '__| / __|\n"
                + "| . \\| |  | \\__ \\\n"
                + "|_|\\_\\_|  |_|___/\n";
        System.out.println("Hello from\n" + logo);
        
        System.out.println("____________________________________________________________");
        System.out.println(" Yo yo yo! I'm Kris, your rap bot extraordinaire!");
        System.out.println(" Drop me some beats... I mean commands! What can I do for you?");
        System.out.println("____________________________________________________________");
        
        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = new ArrayList<>();
        
        while (true) {
            input = scanner.nextLine();
            
            try {
                if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Peace out! Keep it real, catch you on the flip side!");
                System.out.println(" Hope to see you again soon, my friend!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (tasks.isEmpty()) {
                    System.out.println(" Yo! Your task list is empty, time to get busy!");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark")) {
                if (input.trim().equals("mark") || input.substring(4).trim().isEmpty()) {
                    throw new MissingParameterException("mark", "task number");
                }
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(index));
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new InvalidTaskNumberException(String.valueOf(index + 1), tasks.size());
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidTaskNumberException(input.substring(5));
                }
            } else if (input.startsWith("unmark")) {
                if (input.trim().equals("unmark") || input.substring(6).trim().isEmpty()) {
                    throw new MissingParameterException("unmark", "task number");
                }
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(index));
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new InvalidTaskNumberException(String.valueOf(index + 1), tasks.size());
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidTaskNumberException(input.substring(7));
                }
            } else if (input.startsWith("todo")) {
                if (input.trim().equals("todo") || input.substring(4).trim().isEmpty()) {
                    throw new EmptyDescriptionException("todo");
                }
                String description = input.substring(5);
                Task newTask = new Todo(description);
                tasks.add(newTask);
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + newTask);
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("deadline")) {
                if (input.trim().equals("deadline") || input.substring(8).trim().isEmpty()) {
                    throw new EmptyDescriptionException("deadline");
                }
                int byIndex = input.indexOf("/by ");
                if (byIndex == -1) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Yo! Use '/by' to specify the deadline!");
                    System.out.println("____________________________________________________________");
                } else {
                    String description = input.substring(9, byIndex).trim();
                    String by = input.substring(byIndex + 4).trim();
                    Task newTask = new Deadline(description, by);
                    tasks.add(newTask);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTask);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } else if (input.startsWith("event ")) {
                int fromIndex = input.indexOf("/from ");
                int toIndex = input.indexOf("/to ");
                if (fromIndex == -1 || toIndex == -1) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Yo! Use '/from' and '/to' to specify the event time!");
                    System.out.println("____________________________________________________________");
                } else {
                    String description = input.substring(6, fromIndex).trim();
                    String from = input.substring(fromIndex + 6, toIndex).trim();
                    String to = input.substring(toIndex + 4).trim();
                    Task newTask = new Event(description, from, to);
                    tasks.add(newTask);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTask);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } else if (input.startsWith("delete")) {
                if (input.trim().equals("delete") || input.substring(6).trim().isEmpty()) {
                    throw new MissingParameterException("delete", "task number");
                }
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task deletedTask = tasks.remove(index);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + deletedTask);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new InvalidTaskNumberException(String.valueOf(index + 1), tasks.size());
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidTaskNumberException(input.substring(7));
                }
                } else {
                    throw new InvalidCommandException(input);
                }
            } catch (EmptyDescriptionException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Make sure to include a description after the command!");
                System.out.println("____________________________________________________________");
            } catch (MissingParameterException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Check the format: deadline [task] /by [time], event [task] /from [start] /to [end]");
                System.out.println("____________________________________________________________");
            } catch (InvalidTaskNumberException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Use 'list' to see your tasks and their numbers!");
                System.out.println("____________________________________________________________");
            } catch (InvalidCommandException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(" Try these commands:");
                System.out.println(" - todo [description]");
                System.out.println(" - deadline [description] /by [time]");
                System.out.println(" - event [description] /from [start] /to [end]");
                System.out.println(" - list, mark [number], unmark [number], delete [number], bye");
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}