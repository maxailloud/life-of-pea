package com.lop.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Generator {

    public void createPlayer(int playerCount, int rank, World world, MyGame game, GameControllerListener  controllerListener, Array<Player> players){

		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(5f * rank - (float)(playerCount - 1) * 5f /2f, 2.01f);

		Body body = world.createBody(bodyDef);
		Player player = new Player(rank, game, body);
		if (null != controllerListener) {
			controllerListener.addPlayer(player);
		}
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

    public Platform createPlatform(float x, float y, float width, float height, World world, MyGame game){
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyDef.BodyType.KinematicBody;

		Body body = world.createBody(bodyDef);
		body.setLinearDamping(1f);
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new float[]{0, 0,
			width, 0,
			width, height,
			0, height


		});

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.friction = 0.5f;
		body.createFixture(fixtureDef);
		Platform platform = new Platform(body, game);
		body.setUserData(platform);

		rectangle.dispose();

        return platform;
	}
}
