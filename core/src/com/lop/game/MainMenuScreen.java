package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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
	
	private Sprite titleSprite;
	private Sprite gameDevSprite;
	public Generator generator = new Generator();

	private Sprite bronzeStar, silverStar;

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
		button.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 78);

		button.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.clickSound.play();
				start();
			}
		});
		Texture titleTex = new Texture(Gdx.files.internal("life.png"));
		titleTex.setFilter(TextureFilter.MipMapLinearNearest,
				TextureFilter.Linear);
		Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_2D);
		Texture gameDevTex = new Texture(Gdx.files.internal("Logo_Game_Dev_Party.png"));
		gameDevTex.setFilter(TextureFilter.MipMapLinearNearest,
				TextureFilter.Linear);
		
		Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_2D);

		 bronzeStar = new Sprite(game.spritesAtlas.findRegion("starSilver"));
		silverStar = new Sprite(game.spritesAtlas.findRegion("starBronze"));

		
		titleSprite = new Sprite(titleTex);
		gameDevSprite = new Sprite(gameDevTex);

		this.addActor(button);

		menuListener = new MenuListener(this);
		Controllers.addListener(menuListener);

		world = new World(new Vector2(0, -90), true);

		//Creation du sol
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
		game.pauseBatch.begin();
			float width = 300;
			float height = width * titleSprite.getHeight() / titleSprite.getWidth() ;
			
			game.pauseBatch.draw(titleSprite, Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() * 0.75f - height / 2, width, height);
		
			width = 100;
			height = width * gameDevSprite.getHeight() / gameDevSprite.getWidth() ;
			game.pauseBatch.draw(gameDevSprite, 0, 10, width, height);
		game.pauseBatch.draw(bronzeStar, Gdx.graphics.getWidth() - 160, 23, 30, 30);

		game.pauseBatch.draw(silverStar, Gdx.graphics.getWidth() - 160, 3, 30, 30);
		game.font12.draw(game.pauseBatch, "Mass * 2",Gdx.graphics.getWidth() - 120, 40);
			game.font12.draw(game.pauseBatch, "Mass * 10",Gdx.graphics.getWidth() - 120, 20);

		game.pauseBatch.end();
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
