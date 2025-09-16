# Kris Task Manager

Kris is a personal task management application with both command-line and graphical user interfaces. It helps you organize your tasks with support for todos, deadlines, and events, featuring advanced sorting capabilities.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/kris/Main.java` file, right-click it, and choose `Run Main.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, the JavaFX GUI window will open, or you can run `src/main/java/kris/Kris.java` for the command-line interface.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Features

### Task Management
- **Todo Tasks**: Simple tasks with descriptions
- **Deadline Tasks**: Tasks with specific due dates and times
- **Event Tasks**: Tasks with start and end times
- **Task Status**: Mark tasks as done or not done
- **Task Search**: Find tasks by keyword
- **Task Deletion**: Remove tasks from your list

### Advanced Sorting
Kris supports multiple sorting options for better task organization:

- **Default Sorting**: `list default` - Sort by task type (Deadlines → Events → Todos), then by completion status, then alphabetically
- **Deadline Sorting**: `list deadline` - Sort deadlines chronologically (earliest first), with other tasks at the end
- **Alphabetical Sorting**: `list description` - Sort all tasks alphabetically by description
- **Status Sorting**: `list status` - Sort by completion status (incomplete tasks first)

### User Interfaces
- **Graphical Interface**: Modern JavaFX-based GUI with chat-style interaction
- **Command Line**: Traditional CLI for power users
- **Profile Pictures**: Customizable user and bot avatars (place images in `src/main/resources/images/`)

## Usage

### Running the Application
- **GUI Mode**: Run `gradle run` or execute `Main.java`
- **CLI Mode**: Execute `Kris.java` directly

### Commands

#### Basic Commands
- `todo DESCRIPTION` - Add a todo task
- `deadline DESCRIPTION /by DATE_TIME` - Add a deadline task
- `event DESCRIPTION /from START_TIME /to END_TIME` - Add an event task
- `list` - Show all tasks
- `mark NUMBER` - Mark task as done
- `unmark NUMBER` - Mark task as not done  
- `delete NUMBER` - Delete a task
- `find KEYWORD` - Search tasks containing keyword
- `bye` - Exit the application

#### Sorting Commands
- `list` - Display tasks in original order
- `list deadline` - Sort by deadline (chronological)
- `list description` - Sort alphabetically
- `list status` - Sort by completion status
- `list default` - Sort by type, status, and description

### Date and Time Formats
Kris supports flexible date/time input:
- `2024-12-25 1800` (YYYY-MM-DD HHMM)
- `Dec 25 2024 6pm`
- `25/12/2024 18:00`
- And many other natural formats

### Data Storage
- Tasks are automatically saved to `data/kris.txt`
- Data persists between application sessions
- Sorting preferences are maintained in storage

## Examples

```
> todo Read CS2103T textbook
Got it. I've added this task:
  [T][ ] Read CS2103T textbook
Now you have 1 tasks in the list.

> deadline Submit assignment /by 2024-12-01 2359
Got it. I've added this task:
  [D][ ] Submit assignment (by: Dec 01 2024 11:59PM)
Now you have 2 tasks in the list.

> list deadline
Here are the tasks in your list (sorted by deadline):
1.[D][ ] Submit assignment (by: Dec 01 2024 11:59PM)
2.[T][ ] Read CS2103T textbook

> mark 1
Nice! I've marked this task as done:
  [D][X] Submit assignment (by: Dec 01 2024 11:59PM)
```

## Development Tools

This project was developed with the assistance of **GitHub Copilot** for code completion and suggestions.
