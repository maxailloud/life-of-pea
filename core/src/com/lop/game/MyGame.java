package com.lop.game;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game {
	public TextureAtlas spritesAtlas;
	public  TweenManager tweenManager;
	
	private Screen menuScreen;
	public GameScreen gameScreen;
	private InputMultiplexer inputs;
	SpriteBatch batch;
	SpriteBatch pauseBatch;

	public Sound gameSound;
	public Sound clickSound;
	public Sound bonusSound;
	public Sound jumpSound;
	public Sound dashSound;
	public Sound victorySound;

    public BitmapFont font12;
    public BitmapFont font24;

	@Override
	public void create () {
		tweenManager = new TweenManager();
		inputs = new InputMultiplexer();
		spritesAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		for(Texture tex : spritesAtlas.getTextures().iterator()){
			tex.setFilter(TextureFilter.MipMapLinearNearest,
					TextureFilter.Linear);
		}
		Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_2D);

		FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("kenvector_future.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 12;
		font12 = freeTypeFontGenerator.generateFont(parameter);
		parameter.size = 24;
		font24 = freeTypeFontGenerator.generateFont(parameter);
		freeTypeFontGenerator.dispose();
		
		menuScreen = new MainMenuScreen(new ScreenViewport(), this);
		gameScreen = new GameScreen(this);
		setScreen(menuScreen);
		batch = new SpriteBatch();
		pauseBatch = new SpriteBatch();
		gameSound = Gdx.audio.newSound(Gdx.files.internal("SmashRunner.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
		bonusSound = Gdx.audio.newSound(Gdx.files.internal("item_grab.wav"));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("player_jump.wav"));
		dashSound = Gdx.audio.newSound(Gdx.files.internal("player_dash.wav"));
		victorySound = Gdx.audio.newSound(Gdx.files.internal("victory1.wav"));
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
}
