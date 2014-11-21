package com.lop.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class MenuListener extends ControllerAdapter {
	private MainMenuScreen menu;
	
	public MenuListener(MainMenuScreen menu) {
		this.menu = menu;
	}
	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		System.out.println(buttonIndex);
		
		return true;
	}
}
