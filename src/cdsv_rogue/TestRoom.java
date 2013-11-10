package cdsv_rogue;

import java.util.ArrayList;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import it.randomtower.engine.entity.Solid;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

import cdsv_rogue.spells.Spell;

public class TestRoom extends World{
	
	private PlayerUnit player;
	private FrogUnit frog;
	private ArrayList<Spell> spells;
	
	private TiledMap room;

	public TestRoom(int id, GameContainer gc) throws SlickException{
		super(id, gc);
	}
	
	//initializes anything right when the state starts
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		super.init(gc, sbg);
		player = new PlayerUnit(320, 240, this);
		frog = new FrogUnit(120, 340, this);
		room = new TiledMap("res/levels/exampleRoom.tmx");
		for(int i = 0; i < room.getObjectCount(0); i++){
			Solid wall = new Solid(room.getObjectX(0, i), room.getObjectY(0, i),
									room.getObjectWidth(0, i), room.getObjectHeight(0, i),
									0, new Image(room.getObjectWidth(0, i), room.getObjectHeight(0, i)));
			add(wall);
		}
		
		player = new PlayerUnit(220, 140, this);
		frog = new FrogUnit(120, 80, this);
		//the add() method adds any Entity to a list, where all of the rendering and updating happens
		add(player);
		add(frog);
		
		spells = new ArrayList<Spell>();
	}
	
	//takes care of any of the rendering and graphics in the state
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.scale(2, 2);
		room.render(0, 0);
		super.render(gc, sbg, g);
	}
	
	//where all of the logic happens
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		super.update(gc, sbg, delta);
		for(Spell s : spells) {
			checkCollision(s, player);
		}
	}

	public void addSpell(Spell s) {
		spells.add(s);
		add(s);
	}
	
	public void removeSpell(Spell s) {
		spells.remove(s);
	}
	
	private void checkCollision(Entity a, Entity b) {
		
	}
}
