public class EventCommand extends Command {
    private String input;
    
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