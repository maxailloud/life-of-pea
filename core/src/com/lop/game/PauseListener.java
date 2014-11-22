package com.lop.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class PauseListener extends ControllerAdapter {
	private GameScreen gameScreen;

	public PauseListener(GameScreen game) {
		this.gameScreen = game;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == 7 && !gameScreen.playerWin){
			gameScreen.pause();
		} else if (buttonIndex == 0 && gameScreen.playerWin) {
			gameScreen.restart();
		}
		return true;
	}
}
