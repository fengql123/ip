public class DeadlineCommand extends Command {
    private String input;
    
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