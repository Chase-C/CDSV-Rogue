package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.TestRoom;
import cdsv_rogue.Unit;

public class LightningBolt extends Spell {

	float dx, dy;
	
	public LightningBolt(TestRoom room, Entity parent, float x, float y, float dx, float dy) {
		super(room, parent, x, y, dx, dy, 2.5f, Color.yellow);
	}

	@Override
	public void activate(Unit u) {
		u.shock(15, 2500);
	}
}