package cdsv_rogue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import cdsv_rogue.spells.Fireball;

public class EnemyUnit extends Unit {

	Unit player;
	int dt;
	
	public EnemyUnit(float x, float y, Room room, Unit player) {
		super(x, y, room);
		setHitBox(0, 0, 16, 16);
		addType("ENEMY");
		
		this.player = player;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.drawRect(x, y, 16, 16);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		move(gc.getInput());
		executeStatusEffects(delta);

		dt += delta;
		if (dt > 1500) {
			if (distToPlayer() < 400) {
				dt = 0;
				float edx = player.dx;
				float edy = player.dy;

				float a = (edx * edx) + (edy * edy) - 25;
				float b = ((player.x - x) * edx) + ((player.y - y) * edy);
				float c = ((player.x - x) * (player.x - x))
						+ ((player.y - y) * (player.y - y));

				float inside = (b * b) - (4 * a * c);
				if (inside < 0) {
					System.out.printf("%g\n", inside);
					return;
				}

				float t = (float) Math.abs(((-b) + (float) Math.sqrt(inside))
						/ (2 * a));

				dx = (player.x - x + (edx * t));
				dy = (player.y - y + (edy * t));

				float length = (float) Math.sqrt((dx * dx) + (dy * dy));
				float mod = 5 / length;

				dx *= mod;
				dy *= mod;

				// System.out.printf("t: %g, b: %g, edx: %g, edy: %g\n", t, b,
				// edx, edy);

				room.addSpell(new Fireball(room, this, x + (dx * 5) + 8, y
						+ (dy * 5) + 8, player.x, player.y, dx, dy));
			}
		}
	}
	
	private float distToPlayer() {
		return (float)Math.sqrt(((x - player.x) * (x - player.x)) + ((y - player.y) * (y - player.y)));
	}
	
	@Override
	public void move(Input i) throws SlickException {
		dx = 0;
		dy = 0;
		float dist = distToPlayer();
		
		if(dist > 300) {
			if(player.x > x) dx = 2 * speedMod;
			else if(player.x < x) dx = -2 * speedMod;
			
			if(player.y > y) dy = 2 * speedMod;
			else if(player.y < y) dy = -2 * speedMod;
		} else if (dist < 100) {
			if(player.x > x) dx = -2 * speedMod;
			else if(player.x < x) dx = 2 * speedMod;
			
			if(player.y > y) dy = -2 * speedMod;
			else if(player.y < y) dy = 2 * speedMod;
		}
		
		if(collide(SOLID, x + dx, y) == null) {
			x += dx;
		}
		if(collide(SOLID, x, y + dy) == null) {
			y += dy;
		}
	}


}
