package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;

import javax.swing.text.Position;

public class Bonus implements Renderable {
    private MyGame game;
    private Sprite sprite;
    private String type;
    private Platform platform;
    private Body body;

    public Bonus(Integer type, MyGame game, Platform platform, Body body) {
        this.game = game;
        this.type = type == 1 ? "starSilver" : "starBronze";
        sprite = new Sprite(game.spritesAtlas.findRegion(this.type));
        this.platform = platform;
        this.body = body;
    }

    public void render(SpriteBatch batch) {
        Vector2 platformPosition = platform.getBody().getPosition();
        batch.draw(sprite, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 2, 2);
    }
}
