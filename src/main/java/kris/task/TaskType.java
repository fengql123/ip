package kris.task;

/**
 * Enumeration representing the different types of tasks in the task management system.
 * Each task type has an associated symbol used for display and file storage.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");
    
    private final String symbol;
    
    /**
     * Constructs a TaskType with the specified symbol.
     *
     * @param symbol The symbol representing this task type.
     */
    TaskType(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Returns the symbol associated with this task type.
     *
     * @return The symbol representing this task type.
     */
    public String getSymbol() {
        return symbol;
    }
    
    @Override
    public String toString() {
        return "[" + symbol + "]";
    }
}