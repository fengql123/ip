package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Task;
import kris.exception.KrisException;

/**
 * Command that marks a task as not completed.
 * Takes a task number and updates the task's completion status to not done.
 */
public class UnmarkCommand extends Command {
    private String input;

    /**
     * Constructs an UnmarkCommand with the specified input containing the task number.
     *
     * @param input The input string containing the task number to unmark.
     */
    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        int taskNumber = Parser.parseTaskNumber(input, "unmark");
        int index = taskNumber - 1;
        tasks.markTask(index, false);
        Task unmarkedTask = tasks.get(index);
        ui.showTaskUnmarked(unmarkedTask);
        storage.save(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
