package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {
	private MyGame game;
	private Sprite sprite;
	private int rank;
	
	private Body body;
	public Player(int rank, MyGame game, Body body){
		this.body = body;
		this.game = game;
		this.rank = rank;
		String path = rank == 1 ? "alienGreen_round" : "alienBlue_round";
		AtlasRegion tex = game.spritesAtlas.findRegion(path);
		sprite = new Sprite(tex);
	}
	public Sprite getSprite(){
		return sprite;
	}
	public Body getBody(){
		return body;
	}
}
