package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;

/**
 * Command that handles the application exit.
 * Displays a goodbye message and terminates the application.
 */
public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
