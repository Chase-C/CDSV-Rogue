package cdsv_rogue;

import it.randomtower.engine.entity.Entity;

public abstract class Unit extends Entity{

	public Unit(float x, float y) {
		super(x, y);
	}
	
	abstract public void move();
	//abstract public void castSpell();
	
}
