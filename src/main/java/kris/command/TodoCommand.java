package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Todo;
import kris.exception.KrisException;

/**
 * Command that creates a new todo task.
 * Parses the user input to create a todo task and adds it to the task list.
 */
public class TodoCommand extends Command {
    private String input;

    /**
     * Constructs a TodoCommand with the specified input containing the task description.
     *
     * @param input The input string containing the todo task description.
     */
    public TodoCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        Todo newTask = Parser.parseTodo(input);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
