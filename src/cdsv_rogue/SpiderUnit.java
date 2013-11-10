package cdsv_rogue;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import cdsv_rogue.spells.Fireball;

public class SpiderUnit extends Unit {

	Unit player;
	int dt;
	
	private Animation animation;

	public SpiderUnit(float x, float y, Room room, Unit player) throws SlickException {
		super(x, y, room);
		setHitBox(0, 0, 16, 16);
		addType("ENEMY");
		
		this.player = player;
		
		//set up animation
		animation = new Animation(new SpriteSheet("res/sprites/bbspider.png", 19, 16), 100);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		animation.draw(x, y);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		move(gc.getInput());
		executeStatusEffects(delta);
		
		dt += delta;
	}
	
	@Override
	public void move(Input i) throws SlickException {
		float dist = distToPlayer();
		
		if(dist < 30 && dt > 800) {
			dt = 0;
			player.damage(20);
		} else if(dist < 400) {
			if(player.x > x) dx = 1.5f * speedMod;
			else if(player.x < x) dx = -1.5f * speedMod;
			
			if(player.y > y) dy = 1.5f * speedMod;
			else if(player.y < y) dy = -1.5f * speedMod;
		}
		
		if(collide(SOLID, x + dx, y) == null) {
			x += dx;
		}
		if(collide(SOLID, x, y + dy) == null) {
			y += dy;
		}
	}
	
	private float distToPlayer() {
		return (float)Math.sqrt(((x - player.x) * (x - player.x)) + ((y - player.y) * (y - player.y)));
	}
}
