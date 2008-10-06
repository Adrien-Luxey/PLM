package lessons.welcome;

public class MethodsBuggle extends bugglequest.core.SimpleBuggle {
	@Override
	public void forward(int i)  { 
		throw new RuntimeException("Pas le droit d'utiliser forward(int) dans cet exercice");
	}

	@Override
	public void backward(int i) {
		throw new RuntimeException("Pas le droit d'utiliser backward(int) dans cet exercice");
	}

	/* BEGIN SOLUTION */
	public void vaChercher() {
		int i = 0;
		while (!isOverBaggle()) {
			i++;
			forward();
		}
		pickUpBaggle();
		while (i>0) {
			backward();
			i--;
		}
		dropBaggle();
	}
	/* END TEMPLATE */

	@Override
	public void run() { 
		for (int i=0; i<7; i++) {
			vaChercher();
			turnRight();
			forward();
			turnLeft();
		}
	} 
}
