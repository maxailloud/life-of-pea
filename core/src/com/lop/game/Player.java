package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Player {
	private MyGame game;
	private Sprite sprite;
	private int rank;
	public Player(int rank, MyGame game){
		this.game = game;
		this.rank = rank;
		String path = rank == 1 ? "alienGreen_round" : "alienBlue_round";
		AtlasRegion tex = game.spritesAtlas.findRegion(path);
		sprite = new Sprite(tex);
	}
	public Sprite getSprite(){
		return sprite;
	}
}
