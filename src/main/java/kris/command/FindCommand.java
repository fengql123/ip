package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.Parser;
import kris.exception.KrisException;

/**
 * Command that finds tasks containing a specified keyword in their description.
 * Performs case-insensitive search and displays matching tasks.
 */
public class FindCommand extends Command {
    private String input;
    
    /**
     * Constructs a FindCommand with the specified input containing the search keyword.
     *
     * @param input The input string containing the keyword to search for.
     */
    public FindCommand(String input) {
        this.input = input;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        String keyword = Parser.parseDescription(input, "find");
        TaskList matchingTasks = new TaskList();
        
        for (int i = 0; i < tasks.size(); i++) {
            try {
                if (tasks.get(i).toString().toLowerCase().contains(keyword.toLowerCase())) {
                    matchingTasks.add(tasks.get(i));
                }
            } catch (Exception e) {
                // Skip invalid indices
            }
        }
        
        ui.showFoundTasks(matchingTasks);
    }
    
    @Override
    public boolean isExit() {
        return false;
    }
}