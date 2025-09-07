package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.task.Event;
import kris.exception.KrisException;

/**
 * Command that creates a new event task.
 * Parses the user input to create an event task with start and end times and adds it to the task list.
 */
public class EventCommand extends Command {
    private String input;

    /**
     * Constructs an EventCommand with the specified input containing the event details.
     *
     * @param input The input string containing the event description, start time, and end time.
     */
    public EventCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        Event newTask = Parser.parseEvent(input);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
