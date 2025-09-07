package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Task;
import kris.exception.KrisException;

/**
 * Command that marks a task as completed.
 * Takes a task number and updates the task's completion status to done.
 */
public class MarkCommand extends Command {
    private String input;
    
    /**
     * Constructs a MarkCommand with the specified input containing the task number.
     *
     * @param input The input string containing the task number to mark.
     */
    public MarkCommand(String input) {
        this.input = input;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        int taskNumber = Parser.parseTaskNumber(input, "mark");
        int index = taskNumber - 1;
        tasks.markTask(index, true);
        Task markedTask = tasks.get(index);
        ui.showTaskMarked(markedTask);
        storage.save(tasks.getTasks());
    }
    
    @Override
    public boolean isExit() {
        return false;
    }
}