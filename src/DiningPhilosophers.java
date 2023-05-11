
import java.util.Scanner;

/**
 * Class DiningPhilosophers
 * Class containing main.
 *
 * @author Mark Kandaleft
 */

public class DiningPhilosophers {
 
    // Default, can be changed with the command line
    public static final int DEFAULT_PHILOSOPHERS = 6;
 
    // Dinning iterations
    public static final int ITERATIONS = 8;
 
    // Shared Monitor to allow synchronization of philosphers
    public static Monitor piMonitor = null;
 
    // Main Method
    public static void main(String[] argv) {

        try {
            // Default, if no argument is available
            int philosophers = DEFAULT_PHILOSOPHERS;
 
            // Ask user for input
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n\n**************Dining Philosophers**************\n\n"
            + "You will be promted to enter a number of philosophers\n" 
            + "Your philosophers will take turns eating, thinking and talking\n"
            + "A philosopher can only eat if the left and right chopsticks\n"
            + "are available and only one philosopher can talk at a time\n\n"
            + "Your input must be a positive integer\n");
            System.out.print("Enter a the number of philosophers: ");
            String input = scanner.nextLine();
            scanner.close();
 
            // User input handling
            if(input.length() > 0){

                try {
                    int num = Integer.parseInt(input);

                    // Check if number is zero
                    if(num == 0) {
                        System.out.println("\nInput: " + input + " is invalid because if no philosopher is at the table, there is no dilemma");
                        System.out.println("System exiting");
                        System.exit(0);
                    }

                    // Check if the number is negative
                    if(num < 0) {
                        System.out.println("\nInput: " + input + " is invalid because a negative philosopher is not accepted at the table");
                        System.out.println("System exiting");
                        System.exit(0);
                    }

                    // Check if number has a decimal
                    if(input.contains(".")) {
                        System.out.println("\nInput: " + input + " is invalid because a philosopher must be whole");
                        System.out.println("System exiting");
                        System.exit(0);
                    }

                    // Use user input
                    else 
                        philosophers = num;
                }
 
                // If the user enters anything else
                catch(NumberFormatException e) {
                    System.out.println("\nInput: " + input + " is an invalid input! It is not even a number");
                    System.out.println("System exiting");
                    System.exit(0);
                }
            }
 
            // Declare number of philosophers allowed in the Monitor
            piMonitor = new Monitor(philosophers);
 
            // Philosophers array
            Philosopher philoArr[] = new Philosopher[philosophers];

            // Start philosophers
            for(int i = 0; i < philosophers; i++) {
                philoArr[i] = new Philosopher();
                philoArr[i].start();
            }
 
            System.out.println (philosophers + " philosopher(s) came in for chinese supper.");
 
            // Join allows main to wait until all philosophers are finished iterating 
            for(int i = 0; i < philosophers; i++)
                philoArr[i].join();
 
            System.out.println("\nAll philosophers ate and spoke to satisfaction.\n" 
            + "System safely terminating.");
        }
        catch(InterruptedException e) {
            System.err.println("Error in main:");
            reportException(e);
            System.exit(1);
        }
    } // End of main
 
    // Outputs exception information
    public static void reportException(Exception poException) {
        System.err.println("Caught exception : " + poException.getClass().getName());
        System.err.println("Message          : " + poException.getMessage());
        System.err.println("Stack Trace      : ");
        poException.printStackTrace(System.err);
    }
}

// The end