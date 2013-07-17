package lessons.sort.pancake;

import jlm.universe.Entity;
import jlm.universe.World;

public class PancakeEntity extends Entity {

	/**
	 * Must exist. Calling PancakeEntity("dummy name") is ok
	 * Part of the copy process 
	 * Must call super(name)
	 * @return A new instance of PancakeEntity
	 */
	public PancakeEntity() {
		super("Pancake Entity");
	}

	/** Copy constructor (used internally) */
	public PancakeEntity(String name) {
		super(name);
	}
	
	/** Instantiation Constructor (used by exercises to setup the world) */ 
	public PancakeEntity(String name, World world) {
		super(name,world);
	}
	
	/** 
	 * A copy method needed by the JLM
	 * @return a new PancakeEntity with the same name as the caller
	 */
	@Override
	public Entity copy() {
		return new PancakeEntity(this.name);
	}

	/**
	 * Flip a certain amount of pancakes in the stack
	 * @param numberOfPancakes : the number of pancakes, 
	 * 			beginning from the top of the stack, that you want to flip.
	 * @throws InvalidPancakeRank : in case you ask to flip less than one or 
	 * 									more than the total amount of pancakes
	 */
	public void flip(int numberOfPancakes) throws InvalidPancakeRank {
		((PancakeWorld) world).flip(numberOfPancakes);
		stepUI();
	}

	/**
	 * Give the radius of a specific pancake among others
	 * @param pancakeNumber : the number of the pancake, beginning from the top of the stack, that you want to get.
	 * @return The radius of the expected pancake
	 * @throws InvalidPancakeRank : in case you ask an invalid pancake number
	 */
	public int getPancakeRadius(int pancakeNumber) throws InvalidPancakeRank{
		return ((PancakeWorld) world).getPancakeRadius(pancakeNumber);
	}

	/** Returns the size of the pancake stack */
	public int getStackSize() {
		return ((PancakeWorld) world).getStackSize();
	}
	
	/**
	 * Returns whether the specific pancake (counting from the stack top) is upside down
	 * @throws InvalidPancakeRank : in case you ask an invalid pancake number
	 */
	public boolean isPancakeUpsideDown(int rank) throws InvalidPancakeRank {
		return ((PancakeWorld) world).isPancakeUpsideDown(rank);
	}
	
	/**
	 * Tell if the stack of pancakes is correctly sorted according to the control freak pancake seller
	 */
	public boolean isSorted() {
		return ( (PancakeWorld) this.world).isSorted();
	}
	
	/** Must exist so that exercises can instantiate the entity (Entity is abstract) 
	 * @throws InvalidPancakeRank : in case you ask to flip less than one or more than the total amount of pancakes
	 */
	@Override
	public void run() throws InvalidPancakeRank {
	}
	
	/** Returns a string representation of the world */
	public String toString(){
		return "PancakeEntity (" + this.getClass().getName() + ")";
	}
}