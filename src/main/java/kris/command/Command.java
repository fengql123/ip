package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.exception.KrisException;

/**
 * Abstract base class for all commands in the Kris task management system.
 * Commands encapsulate actions that users can perform, following the Command pattern.
 */
public abstract class Command {
    /**
     * Executes the command with the given application components.
     *
     * @param tasks The task list to operate on.
     * @param ui The user interface for displaying output.
     * @param storage The storage component for persisting changes.
     * @throws KrisException If the command execution fails.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException;
    
    /**
     * Executes the command and returns the response string for GUI mode.
     *
     * @param tasks The task list to operate on.
     * @param storage The storage component for persisting changes.
     * @return Response string to display in GUI.
     * @throws KrisException If the command execution fails.
     */
    public String executeForGui(TaskList tasks, Storage storage) throws KrisException {
        return getResponse(tasks, storage);
    }
    
    /**
     * Returns the response message for this command in GUI mode.
     * Subclasses should override this to provide specific response messages.
     *
     * @param tasks The task list to operate on.
     * @param storage The storage component for persisting changes.
     * @return Response message string.
     * @throws KrisException If the command execution fails.
     */
    protected String getResponse(TaskList tasks, Storage storage) throws KrisException {
        return "Command executed successfully.";
    }
    
    /**
     * Returns whether this command causes the application to exit.
     *
     * @return true if this command terminates the application, false otherwise.
     */
    public abstract boolean isExit();
}
