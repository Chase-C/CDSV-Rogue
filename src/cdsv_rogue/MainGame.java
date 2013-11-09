package cdsv_rogue;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainGame extends StateBasedGame{
	
	public MainGame(){
		super("Rogue-Like Dungeon Crawler");
	}
	
	/*
	 * sets the different states of the game with an id for each
	 */
	public void initStatesList(GameContainer gc) throws SlickException{
		addState(new TestRoom(1, gc));
		enterState(1);
	}
	
	public static void main(String[] args){
		try{ //the AppGameContainer runs the game
			AppGameContainer appgc = new AppGameContainer(new MainGame());
			appgc.setDisplayMode(640, 480, false);
			appgc.setTargetFrameRate(60);
			appgc.setShowFPS(false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
}
