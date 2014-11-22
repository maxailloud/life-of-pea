package com.lop.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.physics.box2d.Body;

public class GameControllerListener extends ControllerAdapter{
	private List<Player> players; 
	
	public final float SPEED = 10.0f;
	public GameControllerListener() {
		players = new ArrayList<Player>();
	}
	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		int controllerIndex = Controllers.getControllers().indexOf(controller, true);
		if(controllerIndex < players.size() && axisIndex == 1 && Math.abs(value) >= 0.25f)
		{
			Player player = players.get(controllerIndex);
			Body body = player.getBody();
			body.applyLinearImpulse(value * SPEED * 5, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			if(Math.abs(body.getLinearVelocity().x) > SPEED)
				body.setLinearVelocity(Math.signum(body.getLinearVelocity().x) * SPEED, body.getLinearVelocity().y);
			return true;
		}
		return false;
	}
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		int controllerIndex = Controllers.getControllers().indexOf(controller, true);
		if(controllerIndex < players.size() && buttonIndex == 0)
		{
			Player player = players.get(controllerIndex);
			Body body = player.getBody();
			
			body.applyLinearImpulse(0, 100f, body.getWorldCenter().x, body.getWorldCenter().y, true);
			return true;
		}
		return super.buttonUp(controller, buttonIndex);
	}
	public void addPlayer(Player player){
		players.add(player);
	}
}
