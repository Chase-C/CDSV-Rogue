package cdsv_rogue;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import cdsv_rogue.spells.Fireball;

public class EnemyUnit extends Unit {

	Unit player;
	int dt;

	private boolean facingRight;
	private Animation animation, idleRight, idleLeft;
	
	public EnemyUnit(float x, float y, Room room, Unit player) throws SlickException {
		super(x, y, room);
		setHitBox(0, 0, 16, 16);
		addType("ENEMY");
		
		facingRight = true;
		
		this.player = player;
		
		//set up animation
		idleLeft = new Animation(new SpriteSheet("res/sprites/spook_idlef.png", 9, 19), 100);
		idleRight = new Animation(new SpriteSheet("res/sprites/spook_idle.png", 9, 19), 100);
		animation = idleRight;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		if(dx == 0 && dy == 0){
			if(facingRight)
				animation = idleRight;
			else
				animation = idleLeft;
		}
		animation.draw(x, y);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		move(gc.getInput());
		executeStatusEffects(delta);

		dt += delta;
		if (dt > 1000) {
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
		
		if(dx > 0) {
			facingRight = true;
		} else if(dx < 0) {
			facingRight = false;
		}
		
		if(collide(SOLID, x + dx, y) == null) {
			x += dx;
		}
		if(collide(SOLID, x, y + dy) == null) {
			y += dy;
		}
	}


}
