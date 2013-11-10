package cdsv_rogue;

import org.newdawn.slick.*;

import cdsv_rogue.spells.*;

public class PlayerUnit extends Unit{
	
	private boolean ready;
	private boolean facingRight;
	private Animation animation, idleRight, idleLeft, walkLeft, walkRight;
	
	public void setReady(boolean r){
		ready = r;
	}
	
	public PlayerUnit(float x, float y, Room room) throws SlickException{
		super(x, y, room);
		ready = false;
		facingRight = true;
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
		
		//set up animation
				idleRight = new Animation(new SpriteSheet("res/sprites/guy.png", 9, 19), 100);
				idleLeft = new Animation(new SpriteSheet("res/sprites/guyf.png", 9, 19), 100);
				walkLeft = new Animation(new SpriteSheet("res/sprites/guywalkf.png", 9, 19), 100);
				walkRight = new Animation(new SpriteSheet("res/sprites/guywalk.png", 9, 19), 100);
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
		
		g.setColor(Color.red);
		g.drawRect(x, y - 12, 16, 4);
		g.setColor(Color.green);
		g.drawRect(x, y - 12, 16 * health / maxHealth, 4);
		
		String status = "health: " + health + "/" + maxHealth + "	Spells: 1 - " + spells[0].getName() + ", 2 - " + spells[1].getName() + ", 3 - " + spells[2].getName();
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
			facingRight = true;
			if(collide(SOLID, x + 2, y) == null){ 
				dx = 2 * speedMod;
				animation = walkRight;
			}
		}
		if(check("LEFT")){
			facingRight = false;
			if(collide(SOLID, x - 2, y) == null){
				dx = -2 * speedMod;
				animation = walkLeft;
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
