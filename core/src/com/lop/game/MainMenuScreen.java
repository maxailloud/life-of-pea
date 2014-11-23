package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends Stage implements Screen{
	private MyGame game;
	public TextButton button;
    public TextButtonStyle textButtonStyle;
    public BitmapFont font;
    public Skin skin;
	private MenuListener menuListener;
	private World world;
	private Ground ground;
	private Background background;
	public OrthographicCamera camera;

	public MainMenuScreen(Viewport viewport, MyGame myGame) {
		super(viewport);

		Gdx.input.setInputProcessor(this);

		game = myGame;
		game.getInputs().clear();

		game.clickSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));

		font = new BitmapFont();
		font.scale(0.5F);
        skin = new Skin();
        skin.addRegions(game.spritesAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("green_button00");
        textButtonStyle.down = skin.getDrawable("green_button01");
        button = new TextButton("Play", textButtonStyle);
		button.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		button.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.clickSound.play();
				game.gameSound.stop();
				game.gameSound.loop();
				start();
			}
		});

		this.addActor(button);

		menuListener = new MenuListener(this);
		Controllers.addListener(menuListener);

		world = new World(new Vector2(0, -90), true);

		ground = new Ground(game.spritesAtlas);
		background = new Background();
		background.initClouds(game.spritesAtlas);

		camera = new OrthographicCamera(30f * 1.35f, 30f);
		camera.position.y = camera.viewportHeight / 2;
		camera.update();
	}

	@Override
	public void render(float delta) {
		game.batch.setProjectionMatrix(camera.combined);
		this.act(delta);
		ground.render(game.batch);
		background.render(game.batch, game.pauseBatch, 0);
		this.draw();

		world.step(delta, 6, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {


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

	public void start() {
		Controllers.removeListener(menuListener);
		this.getActors().removeValue(button, true);
		game.setScreen(game.gameScreen);
	}

}
