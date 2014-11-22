package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private MyGame game;

	private World world;
	
	private OrthographicCamera cam;
	
	private GameControllerListener controllerListener;

	private Ground ground;

	private Sprite pauseSprite;
	private boolean gamePaused = false;

	public GameScreen(MyGame game) {
		this.game = game;
		
		controllerListener = new GameControllerListener();
		Controllers.addListener(controllerListener);
		Controllers.addListener(new PauseListener(this));

		world = new World(new Vector2(0, -10), true);
		world.setContactListener(controllerListener);
		cam = new OrthographicCamera(30f * 1.35f, 30f);
		cam.translate(0, cam.viewportHeight / 2);
		cam.update();
		
		createPlayer(1);
		createPlayer(2);
		initialGeneration();
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(0, 2);

		Body body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(-50, 0, 100, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 12.4f;
		
		body.createFixture(fixtureDef);

		ground = new Ground(game.spritesAtlas);
		body.setUserData(ground);
		
		edge.dispose();

		pauseSprite = game.spritesAtlas.createSprite("green_panel");
	}
	public void createPlayer(int rank){
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5 * rank - 5, 2);

		Body body = world.createBody(bodyDef);
		Player player = new Player(rank, game, body);
		controllerListener.addPlayer(player);
		body.setUserData(player);

		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 3.5f; 
		fixtureDef.friction = 10.4f;
		
		body.createFixture(fixtureDef);

		circle.dispose();
	}

	@Override
	public void render(float delta) {
        game.batch.setProjectionMatrix(cam.combined);
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body : bodies){
            if(body.getUserData() instanceof Renderable){
                ((Renderable)body.getUserData()).render(game.batch);
            }

        }
		if (!gamePaused) {
			world.step(delta, 6, 2);
		}
		else {
			game.batch.end();
			game.pauseBatch.begin();
			pauseSprite.setPosition((Gdx.graphics.getWidth() / 2) - (pauseSprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (pauseSprite.getHeight() / 2));
			pauseSprite.setScale(2f);
			pauseSprite.draw(game.pauseBatch);
			String fontText = "Pause";
			BitmapFont font = new BitmapFont();
			font.draw(game.pauseBatch, fontText, Gdx.graphics.getWidth() / 2 - font.getBounds(fontText).width/2, Gdx.graphics.getHeight() / 2 + font.getBounds(fontText).height/2);
			game.pauseBatch.end();
			game.batch.begin();
		}
	}

	public void createPlatform(float x, float y, float width, float height){
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.KinematicBody;

		Body body = world.createBody(bodyDef);
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new float[]{0, 0,
			width, 0,
			width, height,
			0, height
			
			
		});

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.friction = 12.4f;
		
		body.createFixture(fixtureDef);
		body.setUserData(new Platform(body, game));
		
		rectangle.dispose();
	}
	public void generatePlatform(float height, float x){
		float platformHeight = 1;
		float width = MathUtils.random(2f, 4f);
		createPlatform(x, height, width, platformHeight);
	}
	public void initialGeneration(){
		float random = 0f;
		float x = MathUtils.random() * cam.viewportWidth * 0.1f;
		for(int i = 3; i < cam.viewportWidth; i++){
			if(MathUtils.random() < 0.8f + random ){
				generatePlatform(i, x + MathUtils.random(0.15f, 0.5f) * ((float)(MathUtils.random(0, 1) * 2 - 1))* cam.viewportWidth);
				random = 0f;
			}
			else if(0.5f + random < 1f)
				random+= 0.1f;
		}
	}
	public void destroyPlatforms(){
		
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
		gamePaused = !gamePaused;
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
