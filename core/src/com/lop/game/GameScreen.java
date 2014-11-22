package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private MyGame game;

	private World world;

	public GameScreen(MyGame game) {
		this.game = game;
		world = new World(new Vector2(0, -10), true);
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100, 300);

		Body body = world.createBody(bodyDef);
		body.setUserData(new Entity());

		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		Fixture fixture = body.createFixture(fixtureDef);

		circle.dispose();

		// First we create a body definition
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(0, 0);

		body = world.createBody(bodyDef);
		body.setUserData(new Entity());
		EdgeShape edge = new EdgeShape();
		edge.set(0, 0, 100, 0);

		fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		fixture = body.createFixture(fixtureDef);

		edge.dispose();
	}
	@Override
	public void render(float delta) {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies){
			game.batch.draw(game.img, body.getPosition().x, body.getPosition().y);
		}
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
