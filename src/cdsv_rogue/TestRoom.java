package cdsv_rogue;

import java.util.ArrayList;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import cdsv_rogue.spells.Spell;

public class TestRoom extends World{
	
	private PlayerUnit player;
	private FrogUnit frog;
	private ArrayList<Spell> spells;
	
	public TestRoom(int id, GameContainer gc) {
		super(id, gc);
	}
	
	//initializes anything right when the state starts
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		super.init(gc, sbg);
		player = new PlayerUnit(320, 240, this);
		frog = new FrogUnit(120, 340, this);
		//the add() method adds any Entity to a list, where all of the rendering and updating happens
		add(player);
		add(frog);
		
		spells = new ArrayList<Spell>();
	}
	
	//takes care of any of the rendering and graphics in the state
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
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
