package cdsv_rogue.spells;

import java.util.Random;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Color;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

public class Blink extends Spell {
	
	public Blink(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		super("Assassin Blink", room, parent, x, y, tx, ty, dx, dy, 2.5f, Color.gray);
		if(collide(SOLID, tx, ty) == null) {
			float finalX, finalY;
			do {
				finalX = tx + (16 * ((new Random()).nextInt(3) - 1));
				finalY = ty + (16 * ((new Random()).nextInt(3) - 1));
			} while(collide(SOLID, finalX, finalY) != null);
			
			parent.x = finalX;
			parent.y = finalY;
		}
		this.destroy();
	}

	@Override
	public void activate(Unit u) {
		u.burn(15, 2500);
	}

}
