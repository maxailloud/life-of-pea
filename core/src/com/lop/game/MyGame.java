package com.lop.game;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game {
	public TextureAtlas spritesAtlas;
	private AssetManager manager;
	public  TweenManager tweenManager;
	
	private Screen menuScreen;
	public GameScreen gameScreen;
	private FPSLogger fpsLog;
	private InputMultiplexer inputs;
	SpriteBatch batch;
	SpriteBatch pauseBatch;

	public Sound gameSound;
	public Sound clickSound;

	@Override
	public void create () {
		tweenManager = new TweenManager();
		manager = new AssetManager();
		fpsLog = new FPSLogger();
		inputs = new InputMultiplexer();
		spritesAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		for(Texture tex : spritesAtlas.getTextures().iterator()){
			tex.setFilter(TextureFilter.MipMapLinearNearest,
					TextureFilter.Linear);
		}
		Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_2D);
		
		menuScreen = new MainMenuScreen(new ScreenViewport(), this);
		gameScreen = new GameScreen(this);
		setScreen(menuScreen);
		batch = new SpriteBatch();
		pauseBatch = new SpriteBatch();
		gameSound = Gdx.audio.newSound(Gdx.files.internal("SmashRunner.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
	}
	@Override
	public void render() {
		tweenManager.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(163f/255f, 195f/255f, 235f/255f, 1);
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
