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
    protected String getResponse(TaskList tasks, Storage storage) {
        return "Peace out! Keep it real, catch you on the flip side!\nHope to see you again soon, my friend!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
