package cdsv_rogue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import cdsv_rogue.spells.Fireball;

public class FrogUnit extends Unit {
	
	Unit enemy;
	int dt;

	public FrogUnit(float x, float y, TestRoom room, Unit enemy) {
		super(x, y, room);
		setHitBox(0, 0, 16, 16);
		addType("FROG");
		
		this.enemy = enemy;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.drawRect(x, y, 16, 16);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		move(gc.getInput());
		executeStatusEffects(delta);
		
		dt += delta;
		if(dt > 100) {
			dt = 0;
			float b = ((enemy.x - x) * enemy.dx) + ((enemy.y - y) * enemy.dy);
			if(b < 0) return;
			
			float a = (enemy.dx * enemy.dx) + (enemy.dy * enemy.dy) - 25;
			float c = ((enemy.x - x) * (enemy.x - x)) + ((enemy.y - y) * (enemy.y - y));
			
			float t = ((-b) + (float)Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
			
			dx = (enemy.x - x + (enemy.dx * t));
			dy = (enemy.y - y + (enemy.dy * t));								
			
			float length = (float)Math.sqrt((dx * dx) + (dy * dy));
			float mod = 5 / length;
			
			dx *= mod;
			dy *= mod;
			
			room.addSpell(new Fireball(room, this, x + (dx * 5) + 8, y + (dy * 5) + 8, dx, dy));
		}
	}
	
	@Override
	public void move(Input i) {
		// TODO Auto-generated method stub

	}

}
