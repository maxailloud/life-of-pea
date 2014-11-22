package com.lop.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game {
	public TextureAtlas spritesAtlas;
	private AssetManager manager;
	private Screen menu;
	private FPSLogger fpsLog;
	private InputMultiplexer inputs;
	SpriteBatch batch;

	@Override
	public void create () {
		manager = new AssetManager();
		fpsLog = new FPSLogger();
		inputs = new InputMultiplexer();
		spritesAtlas =  new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		menu = new MainMenuScreen(new ScreenViewport(), this);
		setScreen(menu);
		batch = new SpriteBatch();
	}
	@Override
	public void render() {
		fpsLog.log();
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		super.render();
		batch.end();
	}
	public InputMultiplexer getInputs() {
		return inputs;
	}
	public AssetManager getAssetManager(){
		return manager;
	}
}
