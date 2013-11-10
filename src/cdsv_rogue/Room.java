package cdsv_rogue;

import java.util.ArrayList;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import it.randomtower.engine.entity.Solid;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

import cdsv_rogue.spells.Spell;

public class Room extends World{
	
	private PlayerUnit player;
	private ArrayList<Spell> spells;
	private ArrayList<Unit> enemies;
	private TiledMap room;
	
	public Room(int id, GameContainer gc) throws SlickException{
		super(id, gc);
	}
	
	//initializes anything right when the state starts
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		super.init(gc, sbg);
		player = new PlayerUnit(320, 240, this);
		room = new TiledMap("res/levels/exampleRoom.tmx");
		for(int i = 0; i < room.getObjectCount(0); i++){
			Solid wall = new Solid(room.getObjectX(0, i), room.getObjectY(0, i),
									room.getObjectWidth(0, i), room.getObjectHeight(0, i),
									0, new Image(room.getObjectWidth(0, i), room.getObjectHeight(0, i)));
			add(wall);
		}
		 player = new PlayerUnit(220, 140, this);
         //the add() method adds any Entity to a list, where all of the rendering and updating happens
         add(player);
         spells = new ArrayList<Spell>();
         enemies = new ArrayList<Unit>();
	}
	
	//takes care of any of the rendering and graphics in the state
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		room.render(0, 0);
		super.render(gc, sbg, g);
	}
	
	//where all of the logic happens
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
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
	
	public void win() throws SlickException{
		for (Unit u : enemies)
			if (!u.dead)
				return;
		Image teleport = new Image("../res/teleport.png");
		teleport.draw(320, 240);
		
	}
	
}