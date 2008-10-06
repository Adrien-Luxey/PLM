package lessons.welcome;

import java.awt.Color;

import lessons.ExerciseTemplated;
import lessons.Lesson;
import bugglequest.core.Buggle;
import bugglequest.core.Direction;
import bugglequest.core.World;

public class MethodsArgs extends ExerciseTemplated {

	public MethodsArgs(Lesson lesson) {
		super(lesson);
		name = "Méthodes avec des paramètres";
		tabName = "Program";

		World myWorld = new World("Buggles Party",7,7);
		new Buggle(myWorld, "Homer", 0, 6, Direction.NORTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Bart", 1, 2, Direction.SOUTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Lisa", 2, 4, Direction.SOUTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Maggie", 3, 1, Direction.SOUTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Marge", 4, 0, Direction.SOUTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Itchy", 5, 3, Direction.NORTH, Color.black, Color.lightGray);
		new Buggle(myWorld, "Scratchy", 6, 5, Direction.NORTH, Color.black, Color.lightGray);

		setup(myWorld);
	}
}
