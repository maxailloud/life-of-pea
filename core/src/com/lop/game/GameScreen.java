package com.lop.game;

import com.badlogic.gdx.Screen;
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
	
	private Player player1, player2;
	public GameScreen(MyGame game) {
		this.game = game;
		world = new World(new Vector2(0, -10), true);
		
		cam = new OrthographicCamera(16f, 9f);
		cam.translate(0, cam.viewportHeight / 2);
		cam.update();
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 30);

		Body body = world.createBody(bodyDef);
		body.setUserData(new Player(1, game));

		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.3f;
		
		Fixture fixture = body.createFixture(fixtureDef);

		circle.dispose();

		// First we create a body definition
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(0, 0);

		body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(0, 0, 100, 0);

		fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 0.4f;
		
		fixture = body.createFixture(fixtureDef);

		edge.dispose();
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
					game.batch.draw(player.getSprite(), body.getPosition().x, body.getPosition().y, shape.getRadius(), shape.getRadius());
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
