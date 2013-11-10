package cdsv_rogue;

import org.newdawn.slick.*;
import it.randomtower.engine.entity.Entity;

//import org.newdawn.slick.*;

public class Projectile extends Entity{

	public Projectile(float x, float y) {
		super(x, y);
		addType("PROJECTILE");
		setHitBox(0, 0, 16, 16);
	}
	
	public void render(GameContainer gc, Graphics g){
		g.setColor(Color.orange);
		g.drawOval(x, y, 16, 16);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		x += 2;
	}
	
	//deletes itself once outside the screen
	public void leftWorldBoundaries(){
		this.destroy();
	}

}
