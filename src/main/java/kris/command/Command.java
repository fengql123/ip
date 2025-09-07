package kris.command;

import kris.TaskList;
import kris.Ui;
import kris.Storage;
import kris.exception.KrisException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws KrisException;

    public abstract boolean isExit();
}
