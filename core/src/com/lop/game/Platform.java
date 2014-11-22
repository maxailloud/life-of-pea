package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Platform implements Renderable {
	private Sprite left, mid, right;
	private Body body;
	public float width;
	public Platform(Body body, MyGame game) {
		this.body = body;
		AtlasRegion tex = game.spritesAtlas.findRegion("grassHalfLeft");
		left = new Sprite(tex);
		tex = game.spritesAtlas.findRegion("grassHalfMid");
		mid = new Sprite(tex);
		tex = game.spritesAtlas.findRegion("grassHalfRight");
		right = new Sprite(tex);

		PolygonShape shape = (PolygonShape)(body.getFixtureList().get(0).getShape());
		Vector2 vertex0 = new Vector2();
		shape.getVertex(0, vertex0);

		Vector2 vertex2 = new Vector2();
		shape.getVertex(2, vertex2);

		Vector2 bounds = vertex2.sub(vertex0);

		width = -bounds.x;
	}
	@Override
	public void render(SpriteBatch batch) {
		for(Fixture fix : body.getFixtureList()){
			PolygonShape shape = (PolygonShape)(fix.getShape());
			Vector2 vertex0 = new Vector2();
			shape.getVertex(0, vertex0);
			
			Vector2 vertex2 = new Vector2();
			shape.getVertex(2, vertex2);
			
			Vector2 bounds = vertex2.sub(vertex0);
			batch.draw(left, body.getPosition().x, body.getPosition().y - bounds.y, width, bounds.y * 2);
		}
	}

	public Body getBody() {
		return body;
	}
}
