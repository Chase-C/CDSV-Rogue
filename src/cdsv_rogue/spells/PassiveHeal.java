package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Color;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class PassiveHeal extends Spell {

	public PassiveHeal(Room room, Entity parent, float x,
			float y, float tx, float ty, float dx, float dy) {
		super("Passive Heal", room, parent, x, y, tx, ty, dx, dy, 200, null);
	}

	@Override
	public void activate(Unit u) {
		((Unit)parent).heal(0.5f);
	}

}
