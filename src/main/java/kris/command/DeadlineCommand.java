package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Deadline;
import kris.exception.KrisException;

/**
 * Command that creates a new deadline task.
 * Parses the user input to create a deadline task with due date and adds it to the task list.
 */
public class DeadlineCommand extends Command {
    private String input;

    /**
     * Constructs a DeadlineCommand with the specified input containing the task details.
     *
     * @param input The input string containing the deadline task description and due date.
     */
    public DeadlineCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        Deadline newTask = Parser.parseDeadline(input);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
