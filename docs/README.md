# Kris - Your Personal Task Manager

![Kris Application](Ui.png)

**Kris** is a desktop application for managing your personal tasks, optimized for use via Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Kris can get your task management done faster than traditional GUI apps.

---

## Quick Start

1. Ensure you have Java 17 or above installed in your Computer.
2. Download the latest `kris.jar` from [here](https://github.com/fengql123/ip/releases).
3. Copy the file to the folder you want to use as the home folder for your Kris.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar kris.jar` command to run the application.
   A GUI similar to the below should appear in a few seconds:

   ![Kris Startup](Ui.png)

5. Type the command in the command box and press Enter to execute it. Some example commands you can try:
   - `list`: Lists all tasks.
   - `todo read book`: Adds a todo task named "read book" to the task list.
   - `deadline return book /by 2/12/2019 1800`: Adds a deadline task to return book by Dec 2nd 2019, 6pm.
   - `delete 3`: Deletes the 3rd task shown in the current list.
   - `bye`: Exits the app.

6. Refer to the Features below for details of each command.

---

## Features

> **Notes about the command format:**
> - Words in `UPPER_CASE` are the parameters to be supplied by the user.
>   e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read book`.
> - Items in square brackets are optional.
>   e.g `list [SORT_TYPE]` can be used as `list` or as `list deadline`.

### Adding a todo task: `todo`

Adds a todo task to the task list.

**Format:** `todo DESCRIPTION`

**Example:**
- `todo read book`
- `todo buy groceries`

**Expected outcome:**
```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
```

### Adding a deadline: `deadline`

Adds a task with a deadline to the task list.

**Format:** `deadline DESCRIPTION /by DATE_TIME`

**Examples:**
- `deadline return book /by 2/12/2019`
- `deadline submit assignment /by 2/12/2019 1800`
- `deadline project meeting /by 2019-12-02 1800`

**Expected outcome:**
```
Got it. I've added this task:
  [D][ ] return book (by: Dec 02 2019)
Now you have 2 tasks in the list.
```

### Adding an event: `event`

Adds an event to the task list.

**Format:** `event DESCRIPTION /from START_TIME /to END_TIME`

**Examples:**
- `event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600`
- `event conference /from 2/12/2019 0900 /to 2/12/2019 1800`

**Expected outcome:**
```
Got it. I've added this task:
  [E][ ] project meeting (from: Dec 02 2019 1400hrs to: Dec 02 2019 1600hrs)
Now you have 3 tasks in the list.
```

### Listing all tasks: `list`

Shows a list of all tasks in the task list with optional sorting.

**Format:** `list [SORT_TYPE]`

**Sort Types:**
- `deadline`: Sort by deadline (chronological order)
- `description`: Sort alphabetically by task description
- `status`: Sort by completion status (incomplete first)
- `default`: Sort by task type, then status, then description

**Examples:**
- `list` - Shows all tasks in original order
- `list deadline` - Shows all tasks sorted by deadline
- `list status` - Shows incomplete tasks first

**Expected outcome:**
```
Here are the tasks in your list (sorted by deadline):
1.[D][ ] return book (by: Dec 02 2019)
2.[E][ ] project meeting (from: Dec 02 2019 1400hrs to: Dec 02 2019 1600hrs)
3.[T][ ] read book
```

### Marking a task as done: `mark`

Marks the specified task as completed.

**Format:** `mark INDEX`

**Example:**
- `mark 2` - Marks the 2nd task in the list as done

**Expected outcome:**
```
Nice! I've marked this task as done:
  [E][X] project meeting (from: Dec 02 2019 1400hrs to: Dec 02 2019 1600hrs)
```

### Marking a task as not done: `unmark`

Marks the specified task as not completed.

**Format:** `unmark INDEX`

**Example:**
- `unmark 2` - Marks the 2nd task in the list as not done

**Expected outcome:**
```
OK, I've marked this task as not done yet:
  [E][ ] project meeting (from: Dec 02 2019 1400hrs to: Dec 02 2019 1600hrs)
```

### Deleting a task: `delete`

Deletes the specified task from the task list.

**Format:** `delete INDEX`

**Example:**
- `delete 3` - Deletes the 3rd task in the list

**Expected outcome:**
```
Noted. I've removed this task:
  [T][ ] read book
Now you have 2 tasks in the list.
```

### Finding tasks by keyword: `find`

Finds tasks whose descriptions contain the given keyword.

**Format:** `find KEYWORD`

**Example:**
- `find book` - Finds all tasks containing "book"

**Expected outcome:**
```
Here are the matching tasks in your list:
1.[D][ ] return book (by: Dec 02 2019)
```

### Exiting the program: `bye`

Exits the program.

**Format:** `bye`

**Expected outcome:** The application closes.

---

## Advanced Sorting Features

Kris provides powerful sorting capabilities to help you organize your tasks:

### Sort by Deadline
Use `list deadline` to sort all deadline tasks chronologically, with non-deadline tasks appearing at the end.

### Sort Alphabetically  
Use `list description` to sort all tasks alphabetically by their description.

### Sort by Completion Status
Use `list status` to show incomplete tasks first, followed by completed tasks.

### Smart Default Sorting
Use `list default` for intelligent sorting that groups tasks by type (Deadlines → Events → Todos), then by completion status, then alphabetically.

---

## Data Storage

Kris automatically saves your tasks to a file called `kris.txt` in the `data` folder. Your tasks are automatically saved when you add, edit, or delete them - no manual saving required!

The data file is created automatically when you first run Kris, and your tasks will be loaded automatically when you restart the application.

---

## Command Summary

| Action | Format | Example |
|--------|--------|---------|
| **Add Todo** | `todo DESCRIPTION` | `todo read book` |
| **Add Deadline** | `deadline DESCRIPTION /by DATE` | `deadline submit report /by 2/12/2019` |
| **Add Event** | `event DESCRIPTION /from START /to END` | `event meeting /from 2/12/2019 1400 /to 2/12/2019 1600` |
| **List Tasks** | `list [SORT_TYPE]` | `list deadline` |
| **Mark Done** | `mark INDEX` | `mark 1` |
| **Mark Undone** | `unmark INDEX` | `unmark 1` |
| **Delete** | `delete INDEX` | `delete 2` |
| **Find** | `find KEYWORD` | `find book` |
| **Exit** | `bye` | `bye` |

---

## Date and Time Formats

Kris supports the following date and time input formats:

- `2/12/2019 1800` (D/M/YYYY HHMM)
- `02/12/2019 1800` (DD/MM/YYYY HHMM)
- `2019-12-02 1800` (YYYY-MM-DD HHMM)
- `2/12/2019` (D/M/YYYY - date only)
- `02/12/2019` (DD/MM/YYYY - date only)
- `2019-12-02` (YYYY-MM-DD - date only)

---

*Made with ❤️ for efficient task management*
