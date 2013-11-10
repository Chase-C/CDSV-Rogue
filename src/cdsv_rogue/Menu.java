package cdsv_rogue;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import it.randomtower.engine.*;

public class Menu extends World{
	
	private Image background;
	private Input input;
	
	public Menu(int id, GameContainer gc) throws SlickException{
		super(id, gc);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException{
		input.clearKeyPressedRecord();
		input.clearMousePressedRecord();
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		input = gc.getInput();
		background = new Image("res/title.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawImage(background, 0, 0);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		if(input.isKeyPressed(Input.KEY_ENTER)){
			sbg.enterState(1);
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
	}

}
