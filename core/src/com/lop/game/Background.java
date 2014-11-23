package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Background {
	private Array<Sprite> group1 = new Array<Sprite>();
	private Array<Sprite> group2 = new Array<Sprite>();
	private Array<Sprite> group3 = new Array<Sprite>();
	private Sprite cloud1;
	private Sprite cloud2;
	private Sprite cloud3;

    public Background(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");

        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Sprite cloud1 = spriteAtlas.createSprite("cloud1");
            cloud1.setPosition(MathUtils.random(0f, 864f), MathUtils.random(0f, 640f));
            cloud1.setScale(0.6f);
            group1.add(cloud1);
        }
        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Sprite cloud2 = spriteAtlas.createSprite("cloud2");
            cloud2.setPosition(MathUtils.random(0f, 864f), MathUtils.random(0f, 640f));
            cloud2.setScale(0.8f);
            group2.add(cloud2);
        }
        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Sprite cloud3 = spriteAtlas.createSprite("cloud3");
            cloud3.setPosition(MathUtils.random(0f, 864f), MathUtils.random(0f, 640f));
            group3.add(cloud3);
        }
    }

    public void render(SpriteBatch spriteBatch, GameScreen gameScreen) {
        gameScreen.game.batch.end();
        gameScreen.game.pauseBatch.begin();
        for (int i = 0; i < group1.size; i++) {
            Sprite sprite = group1.get(i);
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 0.9f), 129f, 71f);
        }
        for (int i = 0; i < group2.size; i++) {
            Sprite sprite = group2.get(i);
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 1.1f), 129f, 71f);
        }
        for (int i = 0; i < group3.size; i++) {
            Sprite sprite = group3.get(i);
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 1.3f), 129f, 71f);
        }
        gameScreen.game.pauseBatch.end();
        gameScreen.game.batch.begin();
    }
}
