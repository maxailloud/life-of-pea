package com.lop.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Generator {

    public void createPlayer(int rank, World world, MyGame game, GameControllerListener  controllerListener, Array<Player> players){

		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(5 * rank - 5, 2);

		Body body = world.createBody(bodyDef);
		Player player = new Player(rank, game, body);
		controllerListener.addPlayer(player);
		players.add(player);
		body.setUserData(player);

		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 3.5f;
		fixtureDef.friction = 10.4f;
		fixtureDef.restitution = 0.01f;

		body.createFixture(fixtureDef);

		circle.dispose();
	}
}
