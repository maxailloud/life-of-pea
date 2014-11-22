package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
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
		this.jumpCollisions = 2;
		String path = rank == 1 ? "alienGreen_round" : "alienBlue_round";
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
		jumpCollisions++;
	}
	public void decrementJumpCollisions(){
		jumpCollisions--;
	}
	public int getJumpCollisions() {
		return jumpCollisions;
	}
	@Override
	public void render(SpriteBatch batch) {
		for(Fixture fix : body.getFixtureList()){
			Shape shape = fix.getShape();
			game.batch.draw(getSprite(), body.getPosition().x - shape.getRadius(), body.getPosition().y - shape.getRadius(), shape.getRadius() * 2, shape.getRadius() * 2);
		}
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
