package com.lop.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class PauseListener extends ControllerAdapter {
	private GameScreen game;

	public PauseListener(GameScreen game) {
		this.game = game;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == 7){
			game.pause();
		}
		return true;
	}
}
