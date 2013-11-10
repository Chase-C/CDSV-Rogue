package cdsv_rogue;

import org.newdawn.slick.*;

public class PlayerUnit extends Unit{
	
	public PlayerUnit(float x, float y) {
		super(x, y);
		setHitBox(0, 0, 32, 32);
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
		g.drawRect(x, y, 32, 32);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		move();
		//casts a spell if the left mouse button is clicked
		if(check("CAST")){
			castSpell();
		}
	}
	
	public void move(){
		//movement code moves the player if no Entities of type SOLID are in the way
		if(check("RIGHT")){ 
			if(collide(SOLID, x + 2, y) == null){ //collide() returns null if there's no collision
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
	
	//casts the current spell selected, since the player will have 3 different spells available
	public void castSpell(){
		//since the player switches between spells, we use a switch statement to check
		//which spell is currently selected
		switch(currentSpell){ 
			case 1:
				castFireball();
				break;
			case 2:
				//second spell
				break;
			case 3:
				//third spell
				break;
		}
	}

}
