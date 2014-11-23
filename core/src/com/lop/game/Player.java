package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;

public class Player implements Renderable{
	private MyGame game;
	private Sprite sprite;
	public int rank;
	
	private Body body;
	
	private int jumpCollisions;
	
	private boolean dead;
	public Player(int rank, MyGame game, Body body){
		this.body = body;
		this.game = game;
		this.rank = rank;
		this.jumpCollisions = 1;
		
		String path = "alien";
		switch(rank){
		case 1: 
			path += "Green_round";
			break;
		case 2: 
			path += "Blue_round";
			break;
		case 3: 
			path += "Pink_round";
			break;
		case 4: 
			path += "Yellow_round";
			break;
		default :
			path += "Yellow_round";
			break;
		}
		AtlasRegion tex = game.spritesAtlas.findRegion(path);
		sprite = new Sprite(tex);
		
		setDead(false);
	}
	public Sprite getSprite(){
		return sprite;
	}
	public Body getBody(){
		return body;
	}
	public void incrementJumpCollisions(){
		jumpCollisions = 1;
	}
	public void decrementJumpCollisions(){
		jumpCollisions = 0;
	}
	public int getJumpCollisions() {
		return jumpCollisions;
	}
	@Override
	public void render(SpriteBatch batch) {
		if(Math.abs(body.getLinearVelocity().x) < 0.25f && body.getLinearVelocity().y > 0 &&  getJumpCollisions() == 1)
			body.setLinearVelocity(0, 0);
		for(Fixture fix : body.getFixtureList()){
			Shape shape = fix.getShape();
			System.out.println(body.getAngle());
			sprite.setRotation(body.getAngle() * MathUtils.radDeg);
			float width = shape.getRadius() * 2;
			float height = shape.getRadius() * 2;
			game.batch.draw(sprite, body.getPosition().x - shape.getRadius(), body.getPosition().y - shape.getRadius(), width / 2f, height / 2f, width, height, 1f, 1f, sprite.getRotation());
					
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

}
