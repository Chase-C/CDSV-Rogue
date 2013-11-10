package cdsv_rogue;

import org.newdawn.slick.*;

import cdsv_rogue.spells.*;

public class PlayerUnit extends Unit{
	
	private boolean ready;
	
	public void setReady(boolean r){
		ready = r;
	}
	
	public PlayerUnit(float x, float y, Room room) {
		super(x, y, room);
		ready = false;
		setHitBox(0, 0, 16, 16);
		addType("PLAYER"); //to be used for collision
		//control schemes can be referred
		
		health = 100;
		maxHealth = 100;
		
		define("RIGHT", Input.KEY_D);
		define("LEFT", Input.KEY_A);
		define("UP", Input.KEY_W);
		define("DOWN", Input.KEY_S);
		define("CAST", Input.MOUSE_LEFT_BUTTON);
		define("NEXT", Input.KEY_ENTER);
		define("SPELL1", Input.KEY_1);
		define("SPELL2", Input.KEY_2);
		define("SPELL3", Input.KEY_3);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		super.render(gc, g);
		g.setColor(Color.blue);
		g.drawRect(x, y, 16, 16);
		
		String status = "health: " + health + "/" + maxHealth + "	Spells: 1 - " + spells[0].getName() + ", 2 - " + spells[1].getName() + ", 3 - " + spells[2].getName();
		
		g.setColor(Color.red);
		g.drawString(status, 460, 60);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		updatePassives();
		move(gc.getInput());
		executeStatusEffects(delta);
	}
	
	public void move(Input i) throws SlickException{
		//movement code moves the player if no Entities of type SOLID are in the way
		dx = 0;
		dy = 0;
		
		if(check("RIGHT")){ 
			if(collide(SOLID, x + 2, y) == null){ 
				dx = 2 * speedMod;
			}
		}
		if(check("LEFT")){
			if(collide(SOLID, x - 2, y) == null){
				dx = -2 * speedMod;
			}
		}
		if(check("UP")){
			if(collide(SOLID, x, y - 2) == null){
				dy = -2 * speedMod;
			}
		}
		if(check("DOWN")){
			if(collide(SOLID, x, y + 2) == null){
				dy = 2 * speedMod;
			}
		}
		if(check("SPELL1"))
			currentSpell = 0;
		if(check("SPELL2"))
			currentSpell = 1;
		if(check("SPELL3"))
			currentSpell = 2;
		
		x += dx;
		y += dy;
		
		if(check("CAST")) {
			int mx = i.getMouseX();
			int my = i.getMouseY();
			float dx = mx - x - 8;
			float dy = my - y - 8; 
			
			float length = (float)Math.sqrt((dx * dx) + (dy * dy));
			float mod = 5 / length;
			
			dx *= mod;
			dy *= mod;
			
			System.out.printf("%d\n", currentSpell);
			System.out.println(spells[currentSpell]);
			spells[currentSpell].cast(room, this, x, y, mx, my, dx, dy);
		}
		if(check("NEXT")){
			if(ready){
				ready = false;
				//change the Room class's TiledMap reference to the next room
				room.roomTransition();
			}
		}
	}

}
