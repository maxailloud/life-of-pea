package com.lop.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;

public class MenuListener extends ControllerAdapter {
	private MainMenuScreen menu;
	
	public MenuListener(MainMenuScreen menu) {
		this.menu = menu;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == 7){
			menu.render = !menu.render;
			
			menu.start();
			Controllers.removeListener(this);
		}
		return true;
	}
}
