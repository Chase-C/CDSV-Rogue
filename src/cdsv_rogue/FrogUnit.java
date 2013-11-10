package cdsv_rogue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class FrogUnit extends Unit {

	public FrogUnit(float x, float y, TestRoom room) {
		super(x, y, room);
		setHitBox(0, 0, 32, 32);
		addType("FROG");
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.drawRect(x, y, 32, 32);
	}
	
	@Override
	public void move(Input i) {
		// TODO Auto-generated method stub

	}

}
