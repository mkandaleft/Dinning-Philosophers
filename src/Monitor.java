import java.util.PriorityQueue;

/**
 * Class Monitor
 * Synchronizes dining philosophers.
 *
 * @author Mark Kandaleft
 */

public class Monitor {

	// Number of philosophers
	private int numPhilosophers;

	// Current state of a philosopher
	private enum STATE {
		THINKING, EATING, WAITING
	}

	// Array to save all the philosopher states
	private STATE[] STATEarr;

	// List of hungry philosophers
	private PriorityQueue<Integer> WAITING;

	// Bool to see if a philosopher is talking
	private boolean isTalking;

	// Initialize Monitor and the chopsticks
	public Monitor(int inputNbrPhilosophers) {

		// Use number of philosophers from user input
		numPhilosophers = inputNbrPhilosophers;
		STATEarr = new STATE[numPhilosophers];

		// Make a list of hungry philosophers
		WAITING = new PriorityQueue<>();

		// At the start, no philosopher is talking
		isTalking = false;

		// All philosophers start out thinking
		for(int i = 0; i < numPhilosophers; i++){
			STATEarr[i] = STATE.THINKING;
		}
	}

	// Grants request to eat when both chopsticks are available, else waits
	public synchronized void pickUp(final int TID) {

		int philosopher = TID - 1;

		// Change state to waiting
		STATEarr[philosopher] = STATE.WAITING;

		// Add philosopher to waiting list
		WAITING.add(TID);

		// If philopher is waiting and their neighbors are not eating they can eat, else waits.
		int pLeft, pRight;
		try {
			while(true) {
				pLeft = (philosopher + 1) % numPhilosophers;
				pRight = (philosopher + (numPhilosophers - 1)) % numPhilosophers;
				if(STATEarr[pLeft] != STATE.EATING &&
						STATEarr[pRight] != STATE.EATING &&
						STATEarr[philosopher] == STATE.WAITING) {

					// Philosopher starts eating
					STATEarr[philosopher] = STATE.EATING;
					
					break;
				} else {
					wait();
				}
			}
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		// Remove philosopher from waiting list
		WAITING.remove();
	}

	// When a philosopher is done eating, they put the chopstiks down and signal
	public synchronized void putDown(final int TID) {

		int philosopher = TID - 1;

		// Philosopher is now thinking
		STATEarr[philosopher] = STATE.THINKING;

		// Allow other threads to see if they can pick up the chopsticks
		notifyAll();
	}

	
	// Only one philosopher at a time is allowed to talk
	public synchronized void requestTalk() {

		// Check if someone is talking
		if(isTalking){
			try {
				wait();
				requestTalk();
			}
			catch (InterruptedException e){
				System.out.println("A philosopher must be respectful when others are talking and wait for his turn");
			}
			isTalking = true;
		}
	}

	// Philosophers notifying others once done
	public synchronized void endTalk() {

		isTalking = false;
		notifyAll();
	}
}

// The end