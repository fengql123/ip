public class UnmarkCommand extends Command {
    private String input;
    
    public UnmarkCommand(String input) {
        this.input = input;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException {
        int taskNumber = Parser.parseTaskNumber(input, "unmark");
        int index = taskNumber - 1;
        tasks.markTask(index, false);
        Task unmarkedTask = tasks.get(index);
        ui.showTaskUnmarked(unmarkedTask);
        storage.save(tasks.getTasks());
    }
    
    @Override
    public boolean isExit() {
        return false;
    }
}