public class TodoCommand extends Command {
    private String input;
    
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