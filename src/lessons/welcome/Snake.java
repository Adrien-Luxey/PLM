package lessons.welcome;

import java.awt.Color;

import lessons.ExerciseTemplated;
import lessons.Lesson;
import bugglequest.core.Buggle;
import bugglequest.core.Direction;
import bugglequest.core.World;

public class Snake extends ExerciseTemplated {

	public Snake(Lesson lesson) {
		super(lesson);
		name = "Monde de serpents";
		tabName = "SnakeBuggle";

		World myWorld = new World("Desert",7,7);
		for (int i=0; i<7;i++) {
			myWorld.getCell(i, 0).putTopWall();
			myWorld.getCell(0, i).putLeftWall();
		}
		
		new Buggle(myWorld, "Snake", 0, 6, Direction.EAST, Color.red, Color.red);

		setup(myWorld);
	}
}
