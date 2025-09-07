package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Task;
import kris.exception.KrisException;

public class MarkCommand extends Command {
    private String input;

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
