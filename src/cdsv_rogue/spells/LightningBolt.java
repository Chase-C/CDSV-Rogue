package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class LightningBolt extends Spell {

	public LightningBolt(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		super("Lightning Bolt", room, parent, x, y, tx, ty, dx, dy, 2.5f, Color.yellow);
		room.addSpell(this);
	}

	@Override
	public void activate(Unit u) {
		u.shock(15, 2500);
	}
}