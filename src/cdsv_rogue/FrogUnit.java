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

	public FrogUnit(float x, float y, Room room, Unit enemy) {
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
			float edx = enemy.dx;
			float edy = enemy.dy;
			
			float a = (edx * edx) + (edy * edy) - 25;
			float b = ((enemy.x - x) * edx) + ((enemy.y - y) * edy);
			float c = ((enemy.x - x) * (enemy.x - x)) + ((enemy.y - y) * (enemy.y - y));
			
			float inside = (b * b) - (4 * a * c);
			if(inside < 0) {
				System.out.printf("%g\n", inside);
				return;
			}
			
			float t = (float)Math.abs(((-b) + (float)Math.sqrt(inside)) / (2 * a));
			
			dx = (enemy.x - x + (edx * t));
			dy = (enemy.y - y + (edy * t));								
			
			float length = (float)Math.sqrt((dx * dx) + (dy * dy));
			float mod = 5 / length;
			
			dx *= mod;
			dy *= mod;
			
			System.out.printf("t: %g, b: %g, edx: %g, edy: %g\n", t, b, edx, edy);
			
			room.addSpell(new Fireball(room, this, x + (dx * 5) + 8, y + (dy * 5) + 8, dx, dy));
		}
	}
	
	@Override
	public void move(Input i) {
		// TODO Auto-generated method stub
	}

}
