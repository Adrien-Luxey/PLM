package lessons.recursion.logo.square;

import plm.universe.turtles.Turtle;

class ScalaFourSquareEntity extends Turtle {

	/* BEGIN TEMPLATE */
	override def run():Unit = {
		/* BEGIN SOLUTION */
		for (i <- 1 to 4) {
			square();
			right(90);
		}
	}
	def square():Unit = {
		for (i <- 1 to 4) {
			forward(100);
			right(90);
		}
		/* END SOLUTION */
	}
	/* END TEMPLATE */
}
