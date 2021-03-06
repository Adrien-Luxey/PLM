package lessons.recursion.hanoi;

import java.awt.Color;
import java.util.Vector;

import lessons.recursion.hanoi.universe.HanoiDisk;
import lessons.recursion.hanoi.universe.HanoiEntity;
import lessons.recursion.hanoi.universe.HanoiWorld;
import plm.core.model.Game;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;

public class InterleavedHanoi extends ExerciseTemplated {

	public InterleavedHanoi(Game game, Lesson lesson) {
		super(game, lesson);
				
		/* Create initial situation */
		HanoiWorld[] myWorlds = new HanoiWorld[3];
		HanoiWorld w;

		Vector<HanoiDisk> slot00 = HanoiDisk.generateHanoiDisks(new Integer[] {7,6,5,4,3,2,1}, Color.white);
		Vector<HanoiDisk> slot01 = HanoiDisk.generateHanoiDisks(new Integer[] {7,6,5,4,3,2,1}, Color.black);
		w = new HanoiWorld(game, "solve(0,1,2,3)",  
				slot00, slot01, new Vector<HanoiDisk>(), new Vector<HanoiDisk>());
		w.setParameter(new Integer[]{0,1,2,3});
		myWorlds[0] = w;
		
		Vector<HanoiDisk> slot10 = HanoiDisk.generateHanoiDisks(new Integer[] {6,5,4,3,2,1}, Color.black);
		Vector<HanoiDisk> slot12 = HanoiDisk.generateHanoiDisks(new Integer[] {6,5,4,3,2,1}, Color.white);
		w = new HanoiWorld(game, "solve(0,2,3,1)",  
				slot10, new Vector<HanoiDisk>(), slot12, new Vector<HanoiDisk>());
		w.setParameter(new Integer[]{0,2,3,1});
		myWorlds[1] = w;
		
		Vector<HanoiDisk> slot20 = HanoiDisk.generateHanoiDisks(new Integer[] {5,4,3,2,1}, Color.white);
		Vector<HanoiDisk> slot23 = HanoiDisk.generateHanoiDisks(new Integer[] {5,4,3,2,1}, Color.black);
		w = new HanoiWorld(game, "solve(0,3,1,2)",  
				slot20, new Vector<HanoiDisk>(), new Vector<HanoiDisk>(), slot23);
		w.setParameter(new Integer[]{0,3,1,2});
		myWorlds[2] = w;

		for (int i=0;i<myWorlds.length;i++) {
			new HanoiEntity("worker",myWorlds[i]);
		}
		
		setup(myWorlds);
	}
}
