package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class MenuListener extends ControllerAdapter {
	private MainMenuScreen menu;
	
	public MenuListener(MainMenuScreen menu) {
		this.menu = menu;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		Gdx.app.log("Controller", "Button down");
		menu.render = !menu.render;
		
		return true;
	}
}
