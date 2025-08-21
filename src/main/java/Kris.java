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
        Task[] tasks = new Task[100];
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
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Yo, that task number doesn't exist in your list!");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Yo, gimme a valid number after 'mark'!");
                    System.out.println("____________________________________________________________");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Yo, that task number doesn't exist in your list!");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Yo, gimme a valid number after 'unmark'!");
                    System.out.println("____________________________________________________________");
                }
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}