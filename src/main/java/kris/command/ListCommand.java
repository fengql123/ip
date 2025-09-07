package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;

/**
 * Command that displays all tasks in the task list.
 * Shows the current status and details of all user tasks.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
