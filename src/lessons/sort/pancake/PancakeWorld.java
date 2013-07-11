package lessons.sort.pancake;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import jlm.core.model.Game;
import jlm.core.model.ProgrammingLanguage;
import jlm.core.ui.WorldView;
import jlm.universe.EntityControlPanel;
import jlm.universe.World;

public class PancakeWorld extends World {

	private int lastModifiedPancake;
	private boolean burnedWorld ;
	
	/** Copy constructor */ 
	public PancakeWorld(PancakeWorld world) {
		super(world);
	}
	/**
	 * Returns the panel which let the user to interact dynamically with the world
	 */
	@Override
	public EntityControlPanel getEntityControlPanel() {
		return new PancakeFlipButtonPanel();
	}
	/** Returns a component able of displaying the world */
	@Override
	public WorldView getView() {
		return new PancakeWorldView(this);
	}
	
	/** 
	 * Regular PancakeWorld constructor
	 * @param name : the name of the world
	 * @param amountOfPancakes : the amount of pancakes in the stack
	 * @param burnedPancake : if we take care of the fact that the pancake is burned on one side
	 */
	public PancakeWorld(String name, int size, boolean burnedPancake) {
		super(name);
		setDelay(200); // Delay (in ms) in default animations
		
		/* Create the pancakes */
		this.pancakeStack =  new Pancake[size];
		for (int i = 0; i < size; i++) 
			pancakeStack[i] = new Pancake(i + 1);
		
		/* Mix them */
		for ( int rank = 0 ; rank < size ; rank++) {			
			if ( Math.random() > 0.5) // Flipping time !
				pancakeStack[rank].flip(); 
			
			if ( Math.random() > 0.5) // Swapping time !
				swap(rank, (int)(Math.random()*size));
		}
		
		this.flipped =false;
		this.lastModifiedPancake = 0 ;
		this.burnedWorld = burnedPancake;
	}
	
	/** Returns a textual description of the differences between the caller and the parameter */
	@Override
	public String diffTo(World o) {
		if (o == null || !(o instanceof PancakeWorld))
			return Game.i18n.tr("This is not a world of pancakes :-(");

		PancakeWorld other = (PancakeWorld) o;
		if (pancakeStack.length != other.pancakeStack.length)
			return Game.i18n.tr("The two worlds are of differing size");

		StringBuffer res = new StringBuffer();
		for ( int i = 0;i< pancakeStack.length;i++) 
			if ( !pancakeStack[i].equals(other.pancakeStack[i])) 
				res.append(" Pancake #"+(i+1)+" differs: "+pancakeStack[i].toString() +" vs "+other.pancakeStack[i].toString()+"\n");

		return res.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof PancakeWorld))
			return false;
		PancakeWorld other = (PancakeWorld) o;
		if (burnedWorld != other.burnedWorld)
			return false;
		if (pancakeStack.length != other.pancakeStack.length)
			return false;
		for ( int i = 0;i< pancakeStack.length;i++) 
			if ( !pancakeStack[i].equals(other.pancakeStack[i]))
				return false;
		return true;
	}
	/** 
	 * Reset the state of the current world to the one passed in argument
	 * @param the world which must be the new start of your current world
	 */
	@Override
	public void reset(World world) {
		PancakeWorld other = (PancakeWorld) world;
		this.pancakeStack = new Pancake[other.pancakeStack.length];
		for (int i=0;i<pancakeStack.length;i++)
			pancakeStack[i] = other.pancakeStack[i].copy();

		this.flipped = other.flipped;
		this.burnedWorld = other.burnedWorld;
		this.lastModifiedPancake = other.lastModifiedPancake;
		super.reset(world);		
	}

	/** Ensures that the provided engine can be used to solve Pancake exercises */ 
	@Override
	public void setupBindings(ProgrammingLanguage lang, ScriptEngine e) throws ScriptException {
		if (lang.equals(Game.PYTHON)) {
			e.eval(
				"def getStackSize():\n" +
				"  return entity.getStackSize()\n" +
				"def getPancakeRadius(rank):\n" +
				"  return entity.getPancakeRadius(rank)\n" +
				"def isPancakeUpsideDown(pancakeNumber):\n"+
				"  return entity.isPancakeUpsideDown(pancakeNumber)\n" +
				"def flip(numberOfPancakes):\n" +
				"  entity.flip(numberOfPancakes)\n"	
				);
		} else {
			throw new RuntimeException("No binding of PancakeWorld for "+lang);
		}
	}

	/* --------------------------------------- */
	private boolean flipped; // Used in order to improve the visual of the flipping
	private Pancake[] pancakeStack; // The stack of pancakes

	private void swap(int from, int to) {
		Pancake temp = pancakeStack[from];
		pancakeStack[from] = pancakeStack[to];
		pancakeStack[to] = temp;
	}
	/**
	 * Flip a certain amount of pancakes in the stack
	 * @param numberOfPancakes : the number of pancakes, beginning from the top of the stack, that you want to flip.
	 * @throws InvalidPancakeRank : in case you ask to flip less than one or more 
	 * 									than the total amount of pancakes
	 */
	public void flip(int numberOfPancakes) throws InvalidPancakeRank {
		if (numberOfPancakes < 1) 
			throw new InvalidPancakeRank(Game.i18n.tr("Asked to flip {0} pancakes, but you must flip at least one", numberOfPancakes));
		if (numberOfPancakes > this.getStackSize()) 
			throw new InvalidPancakeRank(Game.i18n.tr("Asked to flip {0} pancakes, but there is only {1} pancakes on this stack", numberOfPancakes,getStackSize()));
		
		/* Invert the pancakes' position */
		int firstPancake = 0 ;
		int lastPancake = numberOfPancakes-1;
		while ( firstPancake < lastPancake )
			this.swap(firstPancake++, lastPancake--);
		
		/* Change their orientation */
		for (int i = 0 ;i<numberOfPancakes;i++)
			pancakeStack[i].flip();
		
		this.flipped = !this.flipped;
		this.lastModifiedPancake = numberOfPancakes ;
	}
	
	
	/**
	 * Give the index of the last modified pancakes
	 * @return pancakeNumber : the index of the pancake, beginning from the top of the stack, that was modified.
	 */
	public int getLastModifiedPancake() {
		return lastModifiedPancake;
	}
	
	/**
	 * Give the size of a specific pancake among others
	 * @param rank : the number of the pancake, beginning from the top of the stack, that you want to get.
	 * @return The radius of the expected pancake
	 * @throws InvalidPancakeRank : in case you ask an invalid pancake number
	 */
	public int getPancakeRadius(int rank) throws InvalidPancakeRank{
		if ( rank < 0 || rank >= getStackSize())
			throw new InvalidPancakeRank(Game.i18n.tr("Cannot get the radius of pancake #{0} because it's not between 0 and {1}",rank, getStackSize()));

		return pancakeStack[rank].getRadius();
	}
	
	/**
	 * Give the size of the stack of pancakes
	 * @return The number of pancakes in the stack
	 */
	public int getStackSize() {
		return pancakeStack.length;
	}
	

	/**
	 * Tells the parity of the flips, which is used for graphic purpose only
	 */
	public boolean isFlipped() {
		return flipped;
	}

	/**
	 * Tell if a specific pancake, among others, is upside down
	 * @param rank : the number of the pancake, beginning from the top of the stack, that you want to get.
	 * @return If the specific pancake is upside down or not
	 * @throws InvalidPancakeRank : in case you ask an invalid pancake number
	 */
	public boolean isPancakeUpsideDown(int rank) throws InvalidPancakeRank {
		if ( rank < 0 || rank >= getStackSize())
			throw new InvalidPancakeRank(Game.i18n.tr("Cannot get the orientation of pancake #{0} because it's not between 0 and {1}",rank, getStackSize()));

		return pancakeStack[rank].isUpsideDown();
	}
	
	/**
	 * Tells whether the stack of pancakes is correctly sorted according to the control freak pancake seller
	 */
	public boolean isSorted() {
		for ( int rank = 0 ; rank < pancakeStack.length ; rank++) {
			Pancake pancake = pancakeStack[rank];
			if (pancake.getRadius() != rank+1)
				return false;
			if (burnedWorld && pancake.isUpsideDown())
				return false;
		}
		return true;
	}
	
	/** Returns a string representation of the world */
	public String toString(){
		StringBuffer res = new StringBuffer("<PancakeWorld name:"+getName()+" size:"+getStackSize()+">");
		for (Pancake p : pancakeStack) 
			res.append(p.toString());
		res.append("</PancakeWorld>");
		return res.toString();
	}

	/** Returns whether we pay attention to the burned side or not */
	public boolean isBurnedPancake() {
		return burnedWorld;
	}
}

class Pancake {

	private int radius; // Radius of the pancake
	private boolean upsideDown; // True if the burned face is facing the sky, else false
	
	/**
	 * Constructor of the class Pancake
	 * @param radius :the radius of the pancake
	 */
	public Pancake(int size) {
		this.radius = size;
		this.upsideDown = false;
	}
	
	/**
	 * Make a copy of the caller
	 * @return a copy of the method caller
	 */
	public Pancake copy() {
		Pancake p = new Pancake(this.getRadius());
		if ( this.isUpsideDown())
		{
			p.flip();
		}
		return p;
	}
	
	/**
	 * Indicate whether some other pancake is "equal to" this one
	 * @param burnedMatter if we take care of the position of the burned part
	 * @param Pancake other: the other pancake with which to compare
	 * @return If the two pancakes are equals
	 */
	public boolean equals(Pancake other, boolean burnedMatter) {
		return(
				this.getRadius()==other.getRadius() )
			&& (this.isUpsideDown()==other.isUpsideDown()
				|| !burnedMatter);
	}
	
	/**
	 * Flip a pancake, which leads to changing upsideDown
	 */
	public void flip() {
		this.upsideDown = !this.upsideDown;
	}
	
	/**
	 * Give the radius of the pancake
	 * @return The radius of the pancake
	 */
	public int getRadius() {
		return this.radius;
	}
	
	/**
	 * Tell if the pancake is upside down
	 * @return If the pancake is upside down or not
	 */
	public boolean isUpsideDown() {
		return this.upsideDown;
	}
	
	/**
	 * Return a string representation of the pancake
	 * @return A string representation of the pancake
	 */
	public String toString() {
		String s = "< Radius: "+this.getRadius();
		if ( this.isUpsideDown())
		{
			s+=" , upside down";
		}
		s+=" >";
		return s;
	}
	
}
