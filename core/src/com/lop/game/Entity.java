package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	private Sprite sprite;
	private Vector2 position;
	private float rotation;
	
	public Entity(){
		position = new Vector2(0, 0);
		
		setRotation(0f);
		
	}
	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 v){
		position = v;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
