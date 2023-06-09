# Dinning-Philosophers
This is an implementation of the Dining Philosopher's synchronization problem.

The Dining Philosophers occures when a group of philosophers sit around a table with a bowl of rice and chopsticks on each side. However, they cannot all eat at once because they all share chopsticks with the philosopher to the left and right. They also have to respect each other and speak one at a time. 

This implementation uses a monitor, which is a high-level synchronization construct that provides mutual exclusion and condition variables to grant requests to eat. This implementation also allows mutual exclusivity to speaking so they do not interrupt each other.  
