package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class Iceball extends Spell {

	float dx, dy;
	
	public Iceball(Room room, Entity parent, float x, float y, float dx, float dy) {
		super(room, parent, x, y, dx, dy, 2.5f, Color.cyan);
	}

	@Override
	public void activate(Unit u) {
		u.freeze(15, 2500);
	}
}