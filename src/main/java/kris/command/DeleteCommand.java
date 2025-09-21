package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Task;
import kris.exception.KrisException;

/**
 * Command that deletes a task from the task list.
 * Takes a task number and removes the corresponding task from the list.
 */
public class DeleteCommand extends Command {
    private String input;

    /**
     * Constructs a DeleteCommand with the specified input containing the task number.
     *
     * @param input The input string containing the task number to delete.
     */
    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        int taskNumber = Parser.parseTaskNumber(input, "delete");
        int index = taskNumber - 1;
        Task deletedTask = tasks.remove(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    @Override
    protected String getResponse(TaskList tasks, Storage storage) throws KrisException {
        int taskNumber = Parser.parseTaskNumber(input, "delete");
        int index = taskNumber - 1;
        Task deletedTask = tasks.remove(index);
        storage.save(tasks.getTasks());
        return String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                            deletedTask, tasks.size());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
