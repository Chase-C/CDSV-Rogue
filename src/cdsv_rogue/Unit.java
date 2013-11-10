package cdsv_rogue;

import it.randomtower.engine.entity.Entity;

public abstract class Unit extends Entity{
	
	protected int currentSpell;

	public Unit(float x, float y) {
		super(x, y);
	}
	
	//handles the movement of the unit
	abstract public void move();
	
	//casts the current spell selected
	//abstract public void castSpell();
	
	/*
	 * Different methods will represent the different kinds of spells.
	 * 
	 * 1 - fireball
	 * 2, 3 and so on will represent the other spells
	 */
	/*public void castFireball(){
		Projectile fireball = new Projectile(x, y);
		TestRoom.projectiles.add(fireball);
		System.out.println("Projectile added");
	}*/
	
}
