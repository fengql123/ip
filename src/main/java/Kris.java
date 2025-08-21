import java.util.Scanner;

public class Kris {
    public static void main(String[] args) {
        String logo = " _  __     _     \n"
                + "| |/ /_ __(_)___ \n"
                + "| ' /| '__| / __|\n"
                + "| . \\| |  | \\__ \\\n"
                + "|_|\\_\\_|  |_|___/\n";
        System.out.println("Hello from\n" + logo);
        
        System.out.println("____________________________________________________________");
        System.out.println(" Yo yo yo! I'm Kris, your rap bot extraordinaire!");
        System.out.println(" Drop me some beats... I mean commands! What can I do for you?");
        System.out.println("____________________________________________________________");
        
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] tasks = new String[100];
        int taskCount = 0;
        
        while (true) {
            input = scanner.nextLine();
            
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Peace out! Keep it real, catch you on the flip side!");
                System.out.println(" Hope to see you again soon, my friend!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println(" Yo! Your task list is empty, time to get busy!");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}