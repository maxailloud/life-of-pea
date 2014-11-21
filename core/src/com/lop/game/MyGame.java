package com.lop.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MyGame extends Game {
	private MainMenuScreen menu;
	private World world;
	private InputMultiplexer inputs;
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		inputs = new InputMultiplexer();
		world = new World(new Vector2(0, -10), true);

		menu = new MainMenuScreen(this);
		setScreen(menu);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

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
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		super.render();
		batch.end();
	}
	public InputMultiplexer getInputs() {
		return inputs;
	}
}
