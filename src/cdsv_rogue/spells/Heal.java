package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Color;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class Heal extends Spell {

	public Heal(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		super("Heal", room, parent, x, y, tx, ty, dx, dy, 2.5f, Color.green);
		((Unit) parent).heal(25);
		this.destroy();
	}

	@Override
	public void activate(Unit u) {
		// TODO Auto-generated method stub

	}

}
