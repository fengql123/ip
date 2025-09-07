package kris;

import java.util.Scanner;
import kris.task.Task;
import kris.exception.InvalidTaskNumberException;
import kris.exception.EmptyDescriptionException;
import kris.exception.MissingParameterException;
import kris.exception.InvalidDateFormatException;
import kris.exception.InvalidCommandException;
import kris.exception.KrisException;

/**
 * Handles all user interface interactions for the Kris application.
 * Manages input reading, output display, and formatting of messages.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message with application logo and greeting.
     */
    public void showWelcome() {
        String logo = " _  __     _     \n"
                + "| |/ /_ __(_)___ \n"
                + "| ' /| '__| / __|\n"
                + "| . \\| |  | \\__ \\\n"
                + "|_|\\_\\_|  |_|___/\n";
        System.out.println("Hello from\n" + logo);

        showLine();
        System.out.println(" Yo yo yo! I'm Kris, your rap bot extraordinaire!");
        System.out.println(" Drop me some beats... I mean commands! What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void showGoodbye() {
        showLine();
        System.out.println(" Peace out! Keep it real, catch you on the flip side!");
        System.out.println(" Hope to see you again soon, my friend!");
        showLine();
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Reads a line of user input from the console.
     *
     * @return User input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays all tasks in the task list with proper formatting.
     * Shows a message if the list is empty.
     *
     * @param tasks TaskList containing all user tasks.
     */
    public void showTasks(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(" Yo! Your task list is empty, time to get busy!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                } catch (InvalidTaskNumberException e) {
                    // This should not happen since we're using valid indices
                    System.out.println(" Error displaying task " + (i + 1));
                }
            }
        }
        showLine();
    }

    /**
     * Displays confirmation message when a task is successfully added.
     *
     * @param task Task that was added.
     * @param taskCount Total number of tasks after addition.
     */
    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays confirmation message when a task is marked as done.
     *
     * @param task Task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        showLine();
    }

    /**
     * Displays confirmation message when a task is marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        showLine();
    }

    /**
     * Displays confirmation message when a task is deleted.
     *
     * @param task Task that was deleted.
     * @param taskCount Total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        showLine();
    }
    
    /**
     * Displays search results for tasks matching a keyword.
     * Shows all matching tasks or a message if no matches found.
     *
     * @param matchingTasks TaskList containing tasks that match the search keyword.
     */
    public void showFoundTasks(TaskList matchingTasks) {
        showLine();
        if (matchingTasks.isEmpty()) {
            System.out.println(" No matching tasks found in your list!");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                try {
                    System.out.println(" " + (i + 1) + "." + matchingTasks.get(i));
                } catch (InvalidTaskNumberException e) {
                    // This should not happen since we're using valid indices
                    System.out.println(" Error displaying task " + (i + 1));
                }
            }
        }
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(" OOPS!!! " + message);
    }

    public void showEmptyDescriptionError(EmptyDescriptionException e) {
        showError(e.getMessage());
        System.out.println(" Make sure to include a description after the command!");
        showLine();
    }

    public void showMissingParameterError(MissingParameterException e) {
        showError(e.getMessage());
        System.out.println(" Check the format: deadline [task] /by [time], event [task] /from [start] /to [end]");
        showLine();
    }

    public void showInvalidTaskNumberError(InvalidTaskNumberException e) {
        showError(e.getMessage());
        System.out.println(" Use 'list' to see your tasks and their numbers!");
        showLine();
    }

    public void showInvalidDateFormatError(InvalidDateFormatException e) {
        showError(e.getMessage());
        showLine();
    }

    public void showInvalidCommandError(InvalidCommandException e) {
        showError(e.getMessage());
        System.out.println(" Try these commands:");
        System.out.println(" - todo [description]");
        System.out.println(" - deadline [description] /by [time]");
        System.out.println(" - event [description] /from [start] /to [end]");
        System.out.println(" - list, mark [number], unmark [number], delete [number], find [keyword], bye");
        showLine();
    }

    public void showKrisError(KrisException e) {
        showError(e.getMessage());
        showLine();
    }

    public void showLoadingError() {
        showLine();
        System.out.println(" Yo! Couldn't load your previous tasks, starting fresh!");
        showLine();
    }

    public void close() {
        scanner.close();
    }
}
