package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Color;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class RockThrow extends Spell {

	public RockThrow(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		super("RockThrow", room, parent, x, y, tx, ty, dx, dy, 2.5f, Color.gray);
		room.addSpell(this);
	}

	@Override
	public void activate(Unit u) {
		u.stun(15, 1500, 35);
	}

}
