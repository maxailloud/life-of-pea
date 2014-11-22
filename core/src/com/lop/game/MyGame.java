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
	private InputMultiplexer inputs;
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		inputs = new InputMultiplexer();
		
		menu = new MainMenuScreen(this);
		setScreen(menu);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		

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
