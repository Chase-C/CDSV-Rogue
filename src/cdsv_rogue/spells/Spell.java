package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;

import cdsv_rogue.TestRoom;
import cdsv_rogue.Unit;

abstract public class Spell extends Entity {

	TestRoom room;
	Entity parent;
	
	float dx, dy;
	Color color;
	
	float cooldown;
	
	public Spell(TestRoom room, Entity parent, float x, float y, float dx, float dy, float cooldown, Color c) {
		super(x, y);
		addType("SPELL");
		setHitBox(0, 0, 16, 16);
		
		this.room = room;
		this.parent = parent;
		this.dx = dx;
		this.dy = dy;
		this.cooldown = cooldown;
		color = c;
	}

	abstract public void activate(Unit u);

	public void render(GameContainer gc, Graphics g){
		g.setColor(color);
		g.drawOval(x, y, 16, 16);
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
	}
	
	public void checkBounds(float x1, float y1, float x2, float y2){
		if(x < x1 || x > x2 || y < y1 || y > y2) {
			room.removeSpell(this);
			this.destroy();
		}
	}
}
