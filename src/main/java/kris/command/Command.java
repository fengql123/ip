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
     * Returns whether this command causes the application to exit.
     *
     * @return true if this command terminates the application, false otherwise.
     */

    public abstract boolean isExit();
}
