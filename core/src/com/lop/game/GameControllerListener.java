package com.lop.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameControllerListener extends ControllerAdapter implements ContactListener{
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
			
			if(player.getJumpCollisions() > 0)
				body.applyLinearImpulse(0, 100f, body.getWorldCenter().x, body.getWorldCenter().y, true);
			return true;
		}
		return super.buttonUp(controller, buttonIndex);
	}
	public void addPlayer(Player player){
		players.add(player);
	}
	@Override
	public void beginContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		
		if(a.getUserData() instanceof Player){
			Player player = (Player)a.getUserData();
			if(contact.isTouching() && checkJumpCollision(a, b, contact))
				player.incrementJumpCollisions();
			System.out.println(player.getJumpCollisions());
		}
		if(b.getUserData() instanceof Player){
			Player player = (Player)b.getUserData();
			if(contact.isTouching() && checkJumpCollision(b, a, contact))
				player.incrementJumpCollisions();
			System.out.println(player.getJumpCollisions());
		}

		if(a.getUserData() instanceof Bonus || b.getUserData() instanceof Bonus) {
			System.out.println("bonus");
		}
	}
	public boolean checkJumpCollision(Body playerBody, Body b, Contact contact){
		if(playerBody.getPosition().y >= b.getPosition().y + b.getFixtureList().get(0).getShape().getRadius() *2)
			return true;
		return false;
	}
	@Override
	public void endContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		
		if(a.getUserData() instanceof Player){
			Player player = (Player)a.getUserData();
			if(checkJumpCollision(a, b, contact))
				player.decrementJumpCollisions();
			System.out.println(player.getJumpCollisions());
		}
		if(b.getUserData() instanceof Player){
			Player player = (Player)b.getUserData();
			if(checkJumpCollision(b, a, contact))
				player.decrementJumpCollisions();
			System.out.println(player.getJumpCollisions());
		}
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
