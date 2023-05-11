
/**
 * Class Philosopher.
 * Subroutines of the philosophers.
 *
 * @author Mark Kandaleft
 */

public class Philosopher extends Thread {
	
	// Max time an action can take (in milliseconds) 
	private static final long SLEEPY_TIME = 10000;

	// Static variable increaments to assigns each philosopher an ID
	private static int count = 1;

	// The unique ID of each philosopher
	private int TID;

	// Thread constructor
	public Philosopher() {

		setTID();
	}

	// Overridden Thread.run(), each iteration a philosopher will eat, think then talk
	public void run() {

		for(int i = 0; i < DiningPhilosophers.ITERATIONS; i++) {

			// Philosophers will request Monitor permission to eat to ensure there is enough chopsticks
			DiningPhilosophers.piMonitor.pickUp(getTID());
			eat();

			// Philosophers will then think without exclusivity
			DiningPhilosophers.piMonitor.putDown(getTID());
			think();

			// Philosopher might say something smart
			if(Math.random() >= 0.75) {

				// Philosophers will request Monitor permission to talk to ensure no over talking
				DiningPhilosophers.piMonitor.requestTalk();
				talk();
				DiningPhilosophers.piMonitor.endTalk();
			}
			Thread.yield();
		}
	}

	// Eating philosopher will yield until complete	 
	public void eat() {

		try {
			System.out.println("Philosopher " + getTID() + " started eating");
			Thread.yield();
			sleep((long)(Math.random()*SLEEPY_TIME));
			Thread.yield();
			System.out.println("Philosopher " + getTID() + " is done eating");
		}
		catch(InterruptedException e) {
			System.err.println("Philosopher " + getId() + " was interrupted while eating");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	// Thinking philosopher will yield until complete	
	public void think() {

		try {
			System.out.println("Philosopher " + getTID() + " started thinking");
			Thread.yield();
			sleep((long)(Math.random()*SLEEPY_TIME));
			Thread.yield();
			System.out.println("Philosopher " + getTID() + " is done thinking");
		}
		catch(InterruptedException e) {
			System.err.println("Philosopher " + getId() + " was interrupted while thinking");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	
	// Talking philosopher will yield until complete and might say something smart	
	public void talk() {

		System.out.println("Philosopher " + getTID() + " started talking");
		Thread.yield();
		saySomething();
		Thread.yield();
		System.out.println("Philosopher " + getTID() + " has spoken.");
	}

	// Return ID
	private final int getTID() {

		return this.TID;
	}

	// Sets thread ID and increments static count
	private final void setTID() {

		this.TID = count++;
	}

	// List of potentially smart things
	private void saySomething() {

		String[] astrPhrases = {

			"E = mc^2",
			"I think, therefore I eat",
			"I know that I am intelligent, because I know that I know nothing.",
			"The only true wisdom is in the recipe of this szechuan sauce",
			"Two can be greater than 3, but not always",
			"Maybe if I ruled society, we could achieve world piece",
			"Trying to find Waldo, I found myself"
		};

		System.out.println (
			
			"Philosopher " + getTID() + " is stating: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// The end