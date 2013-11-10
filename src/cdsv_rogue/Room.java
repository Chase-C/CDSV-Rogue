package cdsv_rogue;

import java.util.ArrayList;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import it.randomtower.engine.entity.Solid;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

import cdsv_rogue.spells.Spell;

public class Room extends World {

	private PlayerUnit player;
	private FrogUnit frog;
	private ArrayList<Spell> spells;
	private ArrayList<Unit> enemies;
	private TiledMap room;
	private Animation teleport;
	private int currentRoom;
	private Image message;

	public Room(int id, GameContainer gc) throws SlickException {
		super(id, gc);
	}

	// initializes anything right when the state starts
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		super.init(gc, sbg);
		room = new TiledMap("res/levels/exampleRoom.tmx");
		currentRoom = 1;
		message = new Image("res/pressenter.png");
		generateSolids(room);
		player = new PlayerUnit(320, 240, this);
		frog = new FrogUnit(120, 80, this, player);
		// the add() method adds any Entity to a list, where all of the
		// rendering and updating happens
		add(player);
		//add(frog);
		//keep track of enemies
		enemies = new ArrayList<Unit>();
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
		if(enemies.isEmpty()){
			teleport.setPingPong(true);
			teleport.draw(320, 240);
			message.draw(269, 180);
		}
		
	}

	// where all of the logic happens
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
		win();
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
		player.x = player.startx;
		player.y = player.starty;
		ArrayList<Entity> entities = (ArrayList<Entity>) super.getEntities();
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i).isType("SOLID") || entities.get(i).isType("ENEMY") || entities.get(i).isType("SPELLS")){
				entities.get(i).destroy();
			}
		}
		//re-initialize solids, enemies, and player
		switch(currentRoom){ //sets up stuff from the next room
			case 1: //move to second room
				room = new TiledMap("res/levels/exampleRoom2.tmx"); //this should change depending on the current room
				add(frog);
				enemies.add(frog);
				break;
			case 2: //move to third room
				break;
			//and so on for the other rooms
		}
		//anything beyond this line should run regardless of the current room
		generateSolids(room);
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
	

}
