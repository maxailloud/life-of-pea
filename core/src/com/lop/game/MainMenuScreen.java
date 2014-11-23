package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends Stage implements Screen{
	private MyGame game;
	public TextButton button;
    public TextButtonStyle textButtonStyle;
    public Skin skin;
	private MenuListener menuListener;
	private World world;
	private Ground ground;
	private Background background;
	public OrthographicCamera camera;
	public Array<Player> players;
	public Generator generator = new Generator();

	public MainMenuScreen(Viewport viewport, MyGame myGame) {
		super(viewport);

		Gdx.input.setInputProcessor(this);

		game = myGame;
		game.getInputs().clear();

		game.clickSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));

        skin = new Skin();
        skin.addRegions(game.spritesAtlas);
        textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = myGame.font24;
        textButtonStyle.up = skin.getDrawable("green_button00");
        textButtonStyle.down = skin.getDrawable("green_button01");
        button = new TextButton("Play", textButtonStyle);
		button.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		button.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.clickSound.play();
				start();
			}
		});

//		game.font24.draw(game.pauseBatch, "Life Of Pea", 250f, 250f);

		this.addActor(button);

		menuListener = new MenuListener(this);
		Controllers.addListener(menuListener);

		world = new World(new Vector2(0, -90), true);

		//Création du sol
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(0, 2);

		Body body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(-30f * 1.35f / 2f, 0, 30 * 1.35f / 2f, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 12.4f;

		body.createFixture(fixtureDef);

		edge.dispose();

		ground = new Ground(game.spritesAtlas);
		background = new Background();
		background.initClouds(game.spritesAtlas);

		camera = new OrthographicCamera(30f * 1.35f, 30f);
		camera.position.y = camera.viewportHeight / 2;
		camera.update();

		players = new Array<>();
		for(int i = 0; i < Controllers.getControllers().size; i++){
			generator.createPlayer(Controllers.getControllers().size, i, world, game, null, players);
		}
	}

	@Override
	public void render(float delta) {
		game.batch.setProjectionMatrix(camera.combined);

		this.act(delta);

		background.render(game.batch, game.pauseBatch, 0);
		ground.render(game.batch);

		for(Player player: players) {
			player.render(game.batch);
		}
		game.batch.end();
		this.draw();
		game.batch.begin();

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
		game.gameSound.stop();
		game.gameSound.loop();
	}

}
