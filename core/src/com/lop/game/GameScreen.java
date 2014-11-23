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
	public boolean gamePaused = false;

	private Array<Player> players;

	public boolean playerWin = false;
	private Player winner;

	Platform lastPlatform;

	Generator generator = new Generator();

	public GameScreen(MyGame game) {
		this.game = game;

		suspendedOverlaySprite = game.spritesAtlas.createSprite("green_panel");

		cam = new OrthographicCamera(30f * 1.35f, 30f);

		PauseListener pauseListener = new PauseListener(this);
		Controllers.addListener(pauseListener);

		ground = new Ground(game.spritesAtlas);
		background = new Background();
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
		edge.set(-30f * 1.35f / 2f, 0, 30 * 1.35f / 2f, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 12.4f;

		body.createFixture(fixtureDef);

		edge.dispose();

		players = new Array<>();
		playerWin = false;
		winner = null;
		gamePaused = false;

		cam.position.y = cam.viewportHeight / 2;
		cam.update();

		for(int i = 0; i < Controllers.getControllers().size; i++){
			generator.createPlayer(i, world, game, controllerListener, players);
		}
		initialGeneration();

		background.initClouds(game.spritesAtlas);
	}

	@Override
	public void render(float delta) {
        game.batch.setProjectionMatrix(cam.combined);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

		ground.render(game.batch);
		background.render(game.batch, game.pauseBatch, cam.position.y);

        for(Body body : bodies){
			if (!(body.getUserData() instanceof Player)) {
				Vector2 pos = body.getWorldCenter();
				if(pos.y < cam.position.y - cam.viewportHeight * 0.7f && body.getUserData() instanceof Platform){
					world.destroyBody(body);
					do{
					lastPlatform = nextPlatform(lastPlatform);
					} while(lastPlatform.getBody().getWorldCenter().y < cam.position.y + cam.viewportHeight * 0.7f);
				}
				if(body.getUserData() instanceof Renderable){
					if (body.getUserData() instanceof Destroyable && ((Destroyable)body.getUserData()).toBeDestroy) {
						world.destroyBody(body);
					} else {
						((Renderable)body.getUserData()).render(game.batch);
					}
				}
			}
        }

		for(Player player: players) {
			player.render(game.batch);
		}

		if (playerWin) {
			displayWinOverlay();
		}
		else if (!gamePaused) {
			world.step(delta, 6, 2);
			verticalScrolling();
			checkDie();
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
		Array<Player> alivePlayers = new Array<>();

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
			if(pos.y < cam.position.y - cam.viewportHeight * 0.7f && !player.isDead())
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
		Platform platform = generator.createPlatform(x, y, width, height, world, game);

		createBonus(platform);
		return platform;
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
		
		Platform platform2 = createPlatform(newPos.x, newPos.y, MathUtils.random(3f, 6f), 1);
		
		
		Platform returnPlat = platform2;
		while(MathUtils.random() > MathUtils.clamp((float) (1f - 2f /(Math.log(newPos.y))), 0.2f, 1f)){
			Platform platform3;
			if(MathUtils.random() > 0.5f){
				platform3 = createPlatform(newPos.x + platform2.width / 2 +  MathUtils.random(3f, 10f), newPos.y, MathUtils.random(3f, 6f), 1);
			}
			else
			{
				platform3 = createPlatform(newPos.x - platform2.width / 2 -  MathUtils.random(3f, 10f), newPos.y, MathUtils.random(3f, 6f), 1);
			  
			}
			if(MathUtils.random() > 0.5f)
				returnPlat = platform3;
		}
		return returnPlat;
	}
	public Vector2 randomNewPos(Vector2 position)
	{
		float angle = MathUtils.random(30f, 70f) * (float)(((MathUtils.random(0, 1) * 2) -1)) + 90f;
		Vector2 translation = new Vector2(Vector2.X).rotate(angle).scl((float) (MathUtils.random(4f, 6f) * (1f + (Math.sin((90f - angle) * MathUtils.degRad) + 1f) / 5f)));
		return position.add(translation).sub(new Vector2(3, 1));
	}
	public void initialGeneration(){
		
		lastPlatform = createPlatform(-cam.viewportWidth / 4 - 1, 3, 2, 1);
		for(int i = 0; i < 10; i++){
			lastPlatform = nextPlatform(lastPlatform);

		}
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
		displaySuspendedOverlay("Player " + (winner.rank + 1) + " win \\o/");
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
