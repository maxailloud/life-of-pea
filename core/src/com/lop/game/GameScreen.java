package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends Stage implements Screen {
	public MyGame game;

	private World world;
	
	public OrthographicCamera cam;
	
	private GameControllerListener controllerListener;

	private Ground ground;
	private Background background;

	private Sprite suspendedOverlaySprite;
	private boolean gamePaused = false;

	private Array<Player> players;

	public boolean playerWin = false;
	private Player winner;

	Platform lastPlatform;
	public GameScreen(MyGame game) {
		this.game = game;

		suspendedOverlaySprite = game.spritesAtlas.createSprite("green_panel");

		cam = new OrthographicCamera(30f * 1.35f, 30f);

		PauseListener pauseListener = new PauseListener(this);
		Controllers.addListener(pauseListener);

		ground = new Ground(game.spritesAtlas);
		background = new Background(game.spritesAtlas);
	}

	public void init() {
		controllerListener = new GameControllerListener();
		Controllers.addListener(controllerListener);

		world = new World(new Vector2(0, -90), true);
		world.setContactListener(controllerListener);

		//Création du sol
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

		edge.dispose();

		players = new Array<Player>();
		playerWin = false;
		winner = null;
		gamePaused = false;

		cam.position.y = cam.viewportHeight / 2;
		cam.update();

		for(int i = 0; i < Controllers.getControllers().size; i++){
			createPlayer(i);
		}
		initialGeneration();
	}

	public void createPlayer(int rank){
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5 * rank - 5, 2);

		Body body = world.createBody(bodyDef);
		Player player = new Player(rank, game, body);
		controllerListener.addPlayer(player);
		players.add(player);
		body.setUserData(player);

		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 3.5f; 
		fixtureDef.friction = 10.4f;
		fixtureDef.restitution = 0.01f;
		
		body.createFixture(fixtureDef);

		circle.dispose();
	}

	@Override
	public void render(float delta) {
        game.batch.setProjectionMatrix(cam.combined);
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

		ground.render(game.batch);
		background.render(game.pauseBatch, this);

        for(Body body : bodies){
        	Vector2 pos = body.getWorldCenter();
        	if(pos.y < cam.position.y - cam.viewportWidth * 0.7f && body.getUserData() instanceof Platform){
        		world.destroyBody(body);
        		lastPlatform = nextPlatform(lastPlatform);
        	}
            if(body.getUserData() instanceof Renderable){
				if (body.getUserData() instanceof Destroyable && ((Destroyable)body.getUserData()).toBeDestroy) {
					world.destroyBody(body);
				} else {
					((Renderable)body.getUserData()).render(game.batch);
				}
            }
        }

		if (playerWin) {
			displayWinOverlay();
		}
		else if (!gamePaused) {
			world.step(delta, 6, 2);
			verticalScrolling();
//			checkDie();
		}
		else {
			displayPauseOverlay();
		}
		//debugRenderer.render(world, cam.combined);
	}
	public void verticalScrolling(){
		Player higher = null;
		for(Player player : players){
			if(higher == null || player.getBody().getPosition().y > higher.getBody().getPosition().y){
				higher = player;
			}
		}
		if(higher != null){
			Vector2 position = higher.getBody().getPosition();
			if(position.y  > cam.position.y + cam.viewportHeight * 0.2f)
				cam.position.y = position.y - cam.viewportHeight * 0.2f; 
		}
		cam.update();
	}

	public Array<Player> getAlivePlayers() {
		Array<Player> alivePlayers = new Array<Player>();

		for(Player player : players){
			if (!player.isDead()) {
				alivePlayers.add(player);
			}
		}

		return alivePlayers;
	}

	public void checkDie(){
		for(Player player : players){
			Vector2 pos = player.getBody().getPosition();
			if(pos.y < cam.position.y - cam.viewportWidth * 0.7f && !player.isDead())
			{
				die(player);
				players.removeValue(player, true);
			}
		}

		Array<Player> alivePlayers = getAlivePlayers();
		if (1 == alivePlayers.size) {
			playerWin = true;
			winner = players.first();
		}
	}
	public void die(Player player){
		world.destroyBody(player.getBody());
		player.setDead(true);
	}
	public Platform createPlatform(float x, float y, float width, float height){
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.KinematicBody;

		Body body = world.createBody(bodyDef);
		body.setLinearDamping(1f);
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new float[]{0, 0,
			width, 0,
			width, height,
			0, height
			
			
		});

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.friction = 0.5f;
		body.createFixture(fixtureDef);
		Platform platform = new Platform(body, game);
		body.setUserData(platform);
		
		rectangle.dispose();

		createBonus(platform);
		return platform;
	}
	public void generatePlatform(float height, float x){
		float platformHeight = 1;
		float width = MathUtils.random(2f, 4f);
		createPlatform(x, height, width, platformHeight);
	}
	public Platform nextPlatform(Platform platform){
		Vector2 position = platform.getBody().getPosition().add(new Vector2(platform.width /2, 1));
		Vector2 newPos;
		do
		{
			newPos = randomNewPos(position);
		if(newPos.x < -cam.viewportWidth / 2  * 0.4f)
			newPos.x = -cam.viewportWidth / 2 * 0.4f;
		if(newPos.x > cam.viewportWidth / 2 * 0.4f)
			newPos.x = cam.viewportWidth / 2 * 0.4f;
		}while(newPos.x < -cam.viewportWidth / 2  * 0.4f || newPos.x > cam.viewportWidth / 2 * 0.4f);
		return createPlatform(newPos.x, newPos.y, MathUtils.random(3f, 6f), 1);
	}
	public Vector2 randomNewPos(Vector2 position)
	{
		float angle = MathUtils.random(-70f, -40f) * (float)(((MathUtils.random(0, 1)+ 1) -1)) + 90f;
		
		Vector2 translation = new Vector2(Vector2.X).rotate(angle).scl((float) (MathUtils.random(4f, 6f) * (1f + (Math.sin((90f - angle) * MathUtils.degRad) + 1f) / 5f)));
		
		
		
		Vector2 newPos = position.add(translation).sub(new Vector2(3, 1));
		return newPos;
	}
	public void initialGeneration(){
		
		lastPlatform = createPlatform(-cam.viewportWidth / 4 - 1, 3, 2, 1);
		for(int i = 0; i < 10; i++){
			lastPlatform = nextPlatform(lastPlatform);

		}
		/*float x = MathUtils.random() * cam.viewportWidth * 0.1f;
		for(int i = 3; i < cam.viewportWidth; i++){
			if(MathUtils.random() < 0.5f + random ){
				generatePlatform(i, x + MathUtils.random(0.15f, 0.5f) * ((float)(MathUtils.random(0, 1) * 2 - 1))* cam.viewportWidth);
				random = 0f;
			}
			else if(0.5f + random < 1f)
				random+= 0.1f;
		}*/
	}
	public void destroyPlatforms(){
		
	}

	public void createBonus(Platform platform) {
		if(MathUtils.random() <= 0.3f){
			BodyDef bonusBodyDef = new BodyDef();
			Vector2 platformPosition = platform.getBody().getPosition();
	
			bonusBodyDef.position.set(platformPosition.x + platform.width / 2 - 0.5f, platformPosition.y + 1.5f);
			bonusBodyDef.type = BodyType.KinematicBody;
	
			Body bonusBody = world.createBody(bonusBodyDef);
	
			PolygonShape rectangle = new PolygonShape();
			rectangle.set(new float[]{0, 0,
				1, 0,
				1, 1,
				0, 1
			});
	
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.isSensor = true;
			fixtureDef.shape = rectangle;
			fixtureDef.friction = 12.4f;
	
			bonusBody.createFixture(fixtureDef);
	
			int type = 2;
			if(MathUtils.random() <= 0.3f)
				type = 1;
			bonusBody.setUserData(new Bonus(type, game, bonusBody));
		
		}
	}

	public void displayPauseOverlay() {
		displaySuspendedOverlay("Pause");
	}

	public void displayWinOverlay() {
		displaySuspendedOverlay("Joueur " + (winner.rank + 1) + " a gagné");
	}

	public void displaySuspendedOverlay(String message) {
		game.batch.end();
		game.pauseBatch.begin();
		suspendedOverlaySprite.setPosition((Gdx.graphics.getWidth() / 2) - (suspendedOverlaySprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (suspendedOverlaySprite.getHeight() / 2));
		suspendedOverlaySprite.setScale(2f);
		suspendedOverlaySprite.draw(game.pauseBatch);
		BitmapFont font = new BitmapFont();
		font.draw(game.pauseBatch, message, Gdx.graphics.getWidth() / 2 - font.getBounds(message).width/2, Gdx.graphics.getHeight() / 2 + font.getBounds(message).height/2);
		game.pauseBatch.end();
		game.batch.begin();
	}

	public void restart() {
		init();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		init();
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
		gamePaused = !gamePaused;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
