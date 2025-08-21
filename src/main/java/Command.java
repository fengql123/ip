public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    UNKNOWN("");
    
    private final String keyword;
    
    Command(String keyword) {
        this.keyword = keyword;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public static Command fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return UNKNOWN;
        }
        
        String command = input.trim().toLowerCase().split("\\s+")[0];
        
        for (Command cmd : Command.values()) {
            if (cmd.keyword.equals(command)) {
                return cmd;
            }
        }
        
        return UNKNOWN;
    }
}