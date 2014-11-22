package com.lop.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
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
		if(controllerIndex < players.size() && axisIndex == 1)
		{
			if(Math.abs(value) >= 0.25f){
				Player player = players.get(controllerIndex);
				if(!player.isDead()){
					Body body = player.getBody();
					body.applyLinearImpulse(value * SPEED * 5, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
					if(Math.abs(body.getLinearVelocity().x) > SPEED)
						body.setLinearVelocity(Math.signum(body.getLinearVelocity().x) * SPEED, body.getLinearVelocity().y);
					return true;
				}
			}
			
		}
		return false;
	}
	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		int controllerIndex = Controllers.getControllers().indexOf(controller, true);
		if(controllerIndex < players.size() && buttonIndex == 0)
		{
			Player player = players.get(controllerIndex);
			if(!player.isDead()){
				Body body = player.getBody();
				
				if(player.getJumpCollisions() > 0)
					body.applyLinearImpulse(controller.getAxis(1) * SPEED * 20, 520f, body.getWorldCenter().x, body.getWorldCenter().y, true);
			}
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
		
		Bonus bonus = null;
		Player player = null;
		if(a.getUserData() instanceof Bonus && b.getUserData() instanceof Player) {
			bonus = (Bonus)a.getUserData();
			player = (Player)b.getUserData();
		} else if (b.getUserData() instanceof Bonus && a.getUserData() instanceof Player) {
			bonus = (Bonus)b.getUserData();
			player = (Player)a.getUserData();
		}

		if (null != bonus) {
			
			bonus.bonus(player);
			
		}
		
		if(a.getUserData() instanceof Player){
			player = (Player)a.getUserData();
			if(contact.isTouching() && checkJumpCollision(a, b))
				player.incrementJumpCollisions();
			
		}
		if(b.getUserData() instanceof Player){
			player = (Player)b.getUserData();
			if(contact.isTouching() && checkJumpCollision(b, a))
				player.incrementJumpCollisions();
		}

		
		
	}
	public boolean checkJumpCollision(Body playerBody, Body b){
		return playerBody.getPosition().y >= b.getPosition().y + b.getFixtureList().get(0).getShape().getRadius() * 2;
	}
	@Override
	public void endContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		
		if(a.getUserData() instanceof Player){
			Player player = (Player)a.getUserData();
			if(checkJumpCollision(a, b))
				player.decrementJumpCollisions();
		}
		if(b.getUserData() instanceof Player){
			Player player = (Player)b.getUserData();
			if(checkJumpCollision(b, a))
				player.decrementJumpCollisions();
		}
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		
		if(a.getUserData() instanceof Player){
			if(!(b.getUserData() instanceof Player)){
			 //check if contact points are moving downward
		      for (int i = 0; i < contact.getWorldManifold().getNumberOfContactPoints(); i++) {
		          Vector2 pointVel =
		             a.getLinearVelocityFromWorldPoint(contact.getWorldManifold().getPoints()[i]);
		          if ( pointVel.y <= 0 )
		              return;//point is moving down, leave contact solid and exit
		      }
		  
		      //no points are moving downward, contact should not be solid
		      contact.setEnabled(false);
			}
		
		}
		if(b.getUserData() instanceof Player){
			if(!(a.getUserData() instanceof Player)){
				//check if contact points are moving downward
			      for (int i = 0; i < contact.getWorldManifold().getNumberOfContactPoints(); i++) {
			          Vector2 pointVel =
			             b.getLinearVelocityFromWorldPoint(contact.getWorldManifold().getPoints()[i]);
			          if ( pointVel.y <= 0 )
			              return;//point is moving down, leave contact solid and exit
			      }
			  
			      //no points are moving downward, contact should not be solid
			      contact.setEnabled(false);
			}
		}
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
