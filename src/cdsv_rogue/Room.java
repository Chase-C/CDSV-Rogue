package cdsv_rogue;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import it.randomtower.engine.entity.Solid;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import cdsv_rogue.spells.Spell;

public class Room extends World {

	private PlayerUnit player;
	private EnemyUnit enemy;
	private SpiderUnit spider;
	private ArrayList<Spell> spells;
	private ArrayList<Unit> enemies;
	private TiledMap room;
	private Animation teleport;
	
	private int currentRoom;
	private Image message;
	private Sound currentSong;

	public Room(int id, GameContainer gc) throws SlickException {
		super(id, gc);
	}

	// initializes anything right when the state starts
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		
		room = new TiledMap("res/levels/level1.tmx");
		currentRoom = 1;
		currentSong = new Sound("res/sounds/villageLoopable.wav");
		message = new Image("res/pressenter.png");
		generateSolids(room);
		
		player = new PlayerUnit(320, 240, this);
		enemy = new EnemyUnit(120, 80, this, player);
		spider = new SpiderUnit(180, 100, this, player);
		// the add() method adds any Entity to a list, where all of the
		// rendering and updating happens
		add(player);
		add(enemy);
		add(spider);
		// the add() method adds any Entity to a list, where all of the
		//keep track of enemies
		enemies = new ArrayList<Unit>();
		enemies.add(enemy);
		enemies.add(spider);
		//enemies.add(frog);
		spells = new ArrayList<Spell>();
		//animation setup for teleport object
		SpriteSheet teleportFrames = new SpriteSheet("res/tele.png", 16, 16);
		teleport = new Animation(teleportFrames, 120);
	}

	// takes care of any of the rendering and graphics in the state
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		room.render(0, 0);
		super.render(gc, sbg, g);
		//draws the teleporter only if there are no more enemies left in the room
		if(player.ready){
			teleport.setPingPong(true);
			teleport.draw(320, 240);
			//g.drawString("Press Enter to move on!", 230,  260);
			message.draw(269, 180);
		}
	}

	// where all of the logic happens
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
		win();
		
		switch(currentRoom){
			case 1: case 2: case 3: case 4:
				currentSong.loop();
				break;
			case 5: case 6:
				currentSong.play();
				break;
		}
	}

	public void addSpell(Spell s) {
		spells.add(s);
		add(s);
	}

	public void removeSpell(Spell s) {
		spells.remove(s);
	}

	public void win() throws SlickException {
		for (Unit u : enemies)
			if (!u.dead)
				return;
		player.setReady(true);
	}
	
	//runs when the player moves to the next room
	public void roomTransition() throws SlickException{
		//reset all the enemies, solids, and player coordinates
		player.x = player.startx + 16;
		player.y = player.starty + 16;
		player.randSpells();
		player.setReady(false);
		ArrayList<Entity> entities = (ArrayList<Entity>) super.getEntities();
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i).isType("SOLID") || entities.get(i).isType("ENEMY")){
				entities.get(i).destroy();
			}
		}

		switch(currentRoom){ //sets up stuff from the next room
			case 1: //move to second room
				room = new TiledMap("res/levels/level2.tmx"); //this should change depending on the current room
				//enemies.add(frog);
				break;
			case 2: //move to third room
				room = new TiledMap("res/levels/level3.tmx");
				break;
			case 3: //move to fourth room
				room = new TiledMap("res/levels/level4.tmx");
				break;
			case 4: //move to fifth room
				room = new TiledMap("res/levels/level5.tmx");
				break;
			case 5: //move to sixth room
				room = new TiledMap("res/levels/level6.tmx");
				break;
		}
		//anything beyond this line should run regardless of the current room
		if(currentRoom != 6)
			currentRoom++;
		generateSolids(room);
		addEnemies();
		add(player);
	}
	
	//creates Solid objects around the room based on a given TiledMap
	private void generateSolids(TiledMap map) throws SlickException{
		for (int i = 0; i < map.getObjectCount(0); i++) {
			Solid wall = new Solid(map.getObjectX(0, i),
					map.getObjectY(0, i), map.getObjectWidth(0, i),
					map.getObjectHeight(0, i), 0, new Image(
							map.getObjectWidth(0, i), map.getObjectHeight(0,
									i)));
			add(wall);
		}
	}
	
	void addEnemies() throws SlickException {
		int numEnemies = (new Random()).nextInt(5) + 2;
		for (int i = 0; i < numEnemies; i++) {
			int px = (new Random()).nextInt(656) + 1;
			int py = (new Random()).nextInt(496) + 1;
			Unit u;
			if ((new Random()).nextInt(2) == 0) {
				u = new EnemyUnit(px, py, this, player);
			} else {
				u = new SpiderUnit(px, py, this, player);
			}

			Entity other = null;
			ArrayList<Entity> entities = (ArrayList<Entity>) super.getEntities();
			for(Entity e : entities){
				if(e.isType("SOLID")){
					other = u.collideWith(e, u.x, u.y);
				}
			}
			while (other != null && u.distToUnit(player) < 100) {
				u.x = (new Random()).nextInt(656) + 1;
				u.y = (new Random()).nextInt(496) + 1;
				for(Entity e : entities){
					if(e.isType("SOLID")){
						other = u.collideWith(e, u.x, u.y);
					}
				}
			}
			
			add(u);
			enemies.add(u);
		}
	}

	public TiledMap getMap() {
		return room;
	}
}
