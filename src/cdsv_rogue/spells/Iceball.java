package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class Iceball extends Spell {

	public Iceball(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		super("Snowball", room, parent, x, y, tx, ty, dx, dy, 2.5f, Color.cyan);
		room.addSpell(this);
	}

	@Override
	public void activate(Unit u) {
		u.freeze(15, 2500);
	}
}