package cdsv_rogue;

import java.util.ArrayList;

import it.randomtower.engine.World;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TestRoom extends World{

	public TestRoom(int id, GameContainer gc) {
		super(id, gc);
	}
	
	//initializes anything right when the state starts
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		super.init(gc, sbg);
		PlayerUnit player = new PlayerUnit(320, 240);
		//the add() method adds any Entity to a list, where all of the rendering and updating happens
		add(player); 
	}
	
	//takes care of any of the rendering and graphics in the state
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		super.render(gc, sbg, g);
	}
	
	//where all of the logic happens
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		super.update(gc, sbg, delta);
	}

}
