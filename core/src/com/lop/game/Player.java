package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;

public class Player implements Renderable{
	private Sprite sprite;
	public int rank;
	
	private Body body;
	
	private int jumpCollisions;
	
	private boolean dead;
	
	public final float SPEED = 10.0f;
	
	private boolean canDash = false;
	
	private float initialMass;
	
	public Player(int rank, MyGame game, Body body){
		this.body = body;
		this.rank = rank;
		this.jumpCollisions = 1;
		
		String path = "alien";
		switch(rank){
		case 0: 
			path += "Green_round";
			break;
		case 1: 
			path += "Blue_round";
			break;
		case 2: 
			path += "Pink_round";
			break;
		case 3: 
			path += "Yellow_round";
			break;
		case 4: 
			path += "Beige_round";
			break;
		default :
			path += "Beige_round";
			break;
		}
		AtlasRegion tex = game.spritesAtlas.findRegion(path);
		sprite = new Sprite(tex);
		
		setDead(false);
		
		initialMass = body.getMass();
	}
	public Body getBody(){
		return body;
	}
	public void incrementJumpCollisions(){
		jumpCollisions = 1;
		canDash = true;
	}
	public void decrementJumpCollisions(){
		jumpCollisions = 0;
	}
	public int getJumpCollisions() {
		return jumpCollisions;
	}
	public float getRadius(){
		return body.getFixtureList().get(0).getShape().getRadius();
	}
	@Override
	public void render(SpriteBatch batch) {
		if(Math.abs(body.getLinearVelocity().x) < 0.25f && body.getLinearVelocity().y > 0 &&  getJumpCollisions() == 1)
			body.setLinearVelocity(0, 0);
		for(Fixture fix : body.getFixtureList()){
			Shape shape = fix.getShape();
			sprite.setRotation(body.getAngle() * MathUtils.radDeg);
			float width = shape.getRadius() * 2;
			float height = shape.getRadius() * 2;
			batch.draw(sprite, body.getPosition().x - shape.getRadius(), body.getPosition().y - shape.getRadius(), width / 2f, height / 2f, width, height, 1f, 1f, sprite.getRotation());
					
		}
	}
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void scale(float scale) {
		Fixture fix = body.getFixtureList().get(0);
		CircleShape shape = (CircleShape)fix.getShape();
		shape.setRadius(shape.getRadius() * scale);
	}
	public void jump(float axisValue) {
		if(getJumpCollisions() > 0){
			body.setLinearDamping(1f);
			
			Vector2 jump = (new Vector2( 50f *  getMassRatio(), 0).rotate(new Vector2(axisValue, 1f).angle()));
			body.applyLinearImpulse(jump, body.getWorldCenter(), true);
		}
	}
	public void dash(float axisX, float axisY) {
		Vector2 axis = new Vector2(axisX, -axisY);
		Vector2 dash = new Vector2(50f *  getMassRatio(), 0).rotate(axis.angle()).scl(1f, 1f/3f);
		if(canDash){
			canDash = false;
			body.setLinearDamping(1f);
			body.applyLinearImpulse(dash, body.getWorldCenter(), true);
		}
	}
	public void move(float value) {
		body.applyLinearImpulse(value * SPEED * 5 *  getMassRatio(), 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
		if(Math.abs(body.getLinearVelocity().x) > SPEED)
			body.setLinearVelocity(Math.signum(body.getLinearVelocity().x) * SPEED, body.getLinearVelocity().y);
		
	}
	private float getMassRatio(){
		return body.getMass() / initialMass;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
