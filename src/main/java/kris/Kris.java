package kris;

import kris.command.Command;
import kris.exception.KrisException;

/**
 * Main class for the Kris task management application.
 * Kris is a command-line task manager that helps users organize their tasks.
 * It supports todo tasks, deadlines, and events with automatic file persistence.
 */
public class Kris {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Kris application instance.
     * Initializes the UI, storage, and attempts to load existing tasks from the specified file.
     * If loading fails, starts with an empty task list and displays a loading error message.
     *
     * @param filePath Path to the file where tasks are stored and loaded from.
     */
    public Kris(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (KrisException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop.
     * Shows the welcome message and continuously processes user commands until the user exits.
     * Handles all exceptions gracefully by displaying appropriate error messages.
     * Ensures proper cleanup by closing UI resources when exiting.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (KrisException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }

        ui.close();
    }

    /**
     * Generates a response to a user command for GUI mode.
     * Processes the command and returns the appropriate response string.
     *
     * @param input User command string.
     * @return Response string to display in GUI.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.executeForGui(tasks, storage);
        } catch (KrisException e) {
            return "OOPS!!! " + e.getMessage();
        }
    }

    /**
     * Entry point of the Kris application.
     * Creates and runs a new instance of Kris with the default data file path.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Kris("data/kris.txt").run();
    }
}
