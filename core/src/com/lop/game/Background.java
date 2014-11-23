package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Background {
	private Array<Cloud> group1 = new Array<Cloud>();
	private Array<Cloud> group2 = new Array<Cloud>();
	private Array<Cloud> group3 = new Array<Cloud>();

    public Background(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");

        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Cloud cloud1 = new Cloud(spriteAtlas.createSprite("cloud1"));
            cloud1.sprite.setPosition(MathUtils.random(0f, 864f), MathUtils.random(250f, 640f));
            cloud1.sprite.setSize(129f, 71f);
            cloud1.sprite.setScale(0.2f);
            group1.add(cloud1);
        }
        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Cloud cloud2 = new Cloud(spriteAtlas.createSprite("cloud2"));
            cloud2.sprite.setPosition(MathUtils.random(0f, 864f), MathUtils.random(250f, 640f));
            cloud2.sprite.setSize(129f, 71f);
            cloud2.sprite.setScale(0.6f);
            group2.add(cloud2);
        }
        for (int i = 0; i < MathUtils.random(1, 10); i++) {
            Cloud cloud3 = new Cloud(spriteAtlas.createSprite("cloud3"));
            cloud3.sprite.setPosition(MathUtils.random(0f, 864f), MathUtils.random(250f, 640f));
            cloud3.sprite.setSize(129f, 71f);
            cloud3.sprite.setScale(0.8f);
            group3.add(cloud3);
        }
    }

    public void render(SpriteBatch spriteBatch, GameScreen gameScreen) {
        gameScreen.game.batch.end();
        gameScreen.game.pauseBatch.begin();
        Sprite sprite = new Sprite();

        for (int i = 0; i < group1.size; i++) {
            Cloud cloud = group1.get(i);
            sprite = cloud.sprite;
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 0.9f));
        }
        for (int i = 0; i < group2.size; i++) {
            Cloud cloud = group2.get(i);
            sprite = cloud.sprite;
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 1.1f));
        }
        for (int i = 0; i < group3.size; i++) {
            Cloud cloud = group3.get(i);
            sprite = cloud.sprite;
            spriteBatch.draw(sprite, sprite.getX(), sprite.getY() - (gameScreen.cam.position.y * 1.3f));
        }
        gameScreen.game.pauseBatch.end();
        gameScreen.game.batch.begin();
    }
}
