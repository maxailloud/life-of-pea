package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Bonus extends Destroyable implements Renderable {
    private Sprite sprite;
    private String type;
    public Body body;

    public Bonus(Integer type, MyGame game, Body body) {
        this.type = type == 1 ? "starSilver" : "starBronze";
        sprite = new Sprite(game.spritesAtlas.findRegion(this.type));
        this.body = body;
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 2, 2);
    }
}
