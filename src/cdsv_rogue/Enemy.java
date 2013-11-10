package cdsv_rogue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Enemy extends Unit {

	public Enemy(float x, float y, Room room) {
		super(x, y, room);
		setHitBox(0, 0, 16, 16);
		addType("ENEMY");
	}

	@Override
	public void move(Input i) {
		
		
	}
	
	abstract public void render(GameContainer gc, Graphics g) throws SlickException;
}