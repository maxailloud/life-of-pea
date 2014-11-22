package com.lop.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class RestartListener extends ControllerAdapter {
	private GameScreen game;

	public RestartListener(GameScreen game) {
		this.game = game;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == 7){
			game.restart();
			return false;
		}
		return true;
	}
}
