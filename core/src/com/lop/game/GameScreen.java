package com.lop.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private MyGame game;

	private World world;
	
	private OrthographicCamera cam;
	
	private GameControllerListener controllerListener;
	public GameScreen(MyGame game) {
		this.game = game;
		
		controllerListener = new GameControllerListener();
		Controllers.addListener(controllerListener);
		
		world = new World(new Vector2(0, -10), true);
		world.setContactListener(controllerListener);
		cam = new OrthographicCamera(30f * 1.35f, 30f);
		cam.translate(0, cam.viewportHeight / 2);
		cam.update();
		
		createPlayer(1);
		createPlayer(2);
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(0, 0);

		Body body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(-50, 0, 100, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 12.4f;
		
		body.createFixture(fixtureDef);
		
		edge.dispose();
	}
	public void createPlayer(int rank){
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5 * rank - 5, 0);

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
		fixtureDef.restitution = 0.3f;
		
		body.createFixture(fixtureDef);

		circle.dispose();
	}
	@Override
	public void render(float delta) {
		game.batch.setProjectionMatrix(cam.combined);
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies){
			if(body.getUserData() instanceof Player){
				
				Player player = (Player) body.getUserData();
				for(Fixture fix : body.getFixtureList()){
					Shape shape = fix.getShape();
					game.batch.draw(player.getSprite(), body.getPosition().x, body.getPosition().y, shape.getRadius() * 2, shape.getRadius() * 2);
				}
				
			}
			
		}
		world.step(delta, 6, 2);
	}
	public void generatePlatform(float height){
		
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
