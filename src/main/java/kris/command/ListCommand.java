package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.exception.InvalidTaskNumberException;

/**
 * Command that displays all tasks in the task list.
 * Shows the current status and details of all user tasks.
 * Supports sorting options: deadline, description, status, or default.
 */
public class ListCommand extends Command {
    
    public enum SortType {
        NONE,
        DEFAULT,
        DEADLINE,
        DESCRIPTION,
        STATUS
    }
    
    private SortType sortType;
    
    /**
     * Constructs a ListCommand with no sorting.
     */
    public ListCommand() {
        this.sortType = SortType.NONE;
    }
    
    /**
     * Constructs a ListCommand with the specified sorting type.
     *
     * @param sortType The type of sorting to apply.
     */
    public ListCommand(SortType sortType) {
        assert sortType != null : "Sort type should not be null";
        this.sortType = sortType;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "UI should not be null";
        assert storage != null : "Storage should not be null";
        
        applySorting(tasks, storage);
        ui.showTasks(tasks);
    }

    @Override
    protected String getResponse(TaskList tasks, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert storage != null : "Storage should not be null";
        
        applySorting(tasks, storage);
        
        if (tasks.isEmpty()) {
            return "Yo! Your task list is empty, time to get busy!";
        } else {
            String header = getSortHeader();
            StringBuilder response = new StringBuilder(header);
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
    
    /**
     * Applies the specified sorting to the task list and saves to storage.
     */
    private void applySorting(TaskList tasks, Storage storage) {
        switch (sortType) {
            case DEFAULT:
                sortAndSave(tasks, storage, tasks::sortByDefault);
                break;
            case DEADLINE:
                sortAndSave(tasks, storage, tasks::sortByDeadline);
                break;
            case DESCRIPTION:
                sortAndSave(tasks, storage, tasks::sortByDescription);
                break;
            case STATUS:
                sortAndSave(tasks, storage, tasks::sortByStatus);
                break;
            case NONE:
            default:
                // No sorting applied
                break;
        }
    }

    /**
     * Applies sorting using the provided sorting function and saves to storage.
     */
    private void sortAndSave(TaskList tasks, Storage storage, Runnable sortFunction) {
        sortFunction.run();
        saveTasksToStorage(tasks, storage);
    }

    /**
     * Saves tasks to storage, ignoring any storage errors.
     */
    private void saveTasksToStorage(TaskList tasks, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (Exception e) {
            // Ignore storage errors for now
        }
    }
    
    /**
     * Returns the appropriate header based on the sorting type.
     */
    private String getSortHeader() {
        switch (sortType) {
            case DEFAULT:
                return "Here are the tasks in your list (sorted by type and status):\n";
            case DEADLINE:
                return "Here are the tasks in your list (sorted by deadline):\n";
            case DESCRIPTION:
                return "Here are the tasks in your list (sorted alphabetically):\n";
            case STATUS:
                return "Here are the tasks in your list (sorted by completion status):\n";
            case NONE:
            default:
                return "Here are the tasks in your list:\n";
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
