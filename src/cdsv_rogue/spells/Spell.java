package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.Room;
import cdsv_rogue.Unit;

abstract public class Spell extends Entity {

	Room room;
	Entity parent;
	
	float dx, dy;
	Color color;
	
	float cooldown;
	
	public Spell(String name, Room room2, Entity parent, float x, float y, float tx, float ty, float dx, float dy, float cooldown, Color c) {
		super(x, y);
		addType("SPELL");
		setHitBox(0, 0, 16, 16);
		
		this.name = name;
		this.room = room2;
		this.parent = parent;
		this.dx = dx;
		this.dy = dy;
		this.cooldown = cooldown;
		color = c;
	}

	abstract public void activate(Unit u);

	public void render(GameContainer gc, Graphics g){
		g.setColor(color);
		g.drawOval(x, y, 12, 12);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		x += dx;
		y += dy;
		
		Entity other = collide("UNIT", x, y);
		if(other != null && other != parent) {
			activate((Unit)other);
			room.removeSpell(this);
			this.destroy();
		}
		
		/*if(collide(SOLID, x, y) != null) {
			room.removeSpell(this);
			this.destroy();
		}*/
		
		checkBounds(0, 0, 1000, 1000);
		
	}
	
	public void checkBounds(float x1, float y1, float x2, float y2){
		if(x < x1 || x > x2 || y < y1 || y > y2) {
			room.removeSpell(this);
			this.destroy();
		}
	}
}

