package cdsv_rogue;

import org.newdawn.slick.*;

public class PlayerUnit extends Unit{
	
	public PlayerUnit(float x, float y) {
		super(x, y);
		setHitBox(0, 0, 16, 16);
		currentSpell = 1; //sets the current spell to fireball
		addType("PLAYER"); //to be used for collision
		//control schemes can be referred
		define("RIGHT", Input.KEY_D);
		define("LEFT", Input.KEY_A);
		define("UP", Input.KEY_W);
		define("DOWN", Input.KEY_S);
		define("CAST", Input.MOUSE_LEFT_BUTTON);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.blue);
		g.drawRect(x, y, 16, 16);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		move();
	}
	
	public void move(){
		//movement code moves the player if no Entities of type SOLID are in the way
		if(check("RIGHT")){ 
			if(collide(SOLID, x + 2, y) == null){ 
				x += 2;
			}
		}
		if(check("LEFT")){
			if(collide(SOLID, x - 2, y) == null){
				x -= 2;
			}
		}
		if(check("UP")){
			if(collide(SOLID, x, y - 2) == null){
				y -= 2;
			}
		}
		if(check("DOWN")){
			if(collide(SOLID, x, y + 2) == null){
				y += 2;
			}
		}
	}

}
