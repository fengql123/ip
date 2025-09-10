package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.exception.InvalidTaskNumberException;

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
    protected String getResponse(TaskList tasks, Storage storage) {
        if (tasks.isEmpty()) {
            return "Yo! Your task list is empty, time to get busy!";
        } else {
            StringBuilder response = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    response.append(i + 1).append(".").append(tasks.get(i)).append("\n");
                } catch (InvalidTaskNumberException e) {
                    response.append("Error displaying task ").append(i + 1).append("\n");
                }
            }
            return response.toString().trim();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
