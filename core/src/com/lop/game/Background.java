package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Background {
	private Array<Cloud> cloudGroup = new Array<Cloud>();

    public Background(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");

        for (int i = 0; i < 10; i++) {
            Cloud cloud = new Cloud(spriteAtlas.createSprite("cloud" + MathUtils.random(1, 3)));
            cloud.sprite.setSize(129f, 71f);
            initCloud(cloud, i);
            cloudGroup.add(cloud);
        }
    }

    public void initCloud(Cloud cloud, int index) {
        cloud.distanceRatio = MathUtils.random(0.2f, 1.4f);
        cloud.sprite.setPosition(
                MathUtils.random(0f + cloud.sprite.getWidth() / 2,
                864f - cloud.sprite.getHeight() / 2), MathUtils.random(150f, 840f) + (10 * cloud.distanceRatio)
        );
        cloud.sprite.setScale(cloud.distanceRatio / 2);
    }

    public void render(SpriteBatch spriteBatch, GameScreen gameScreen) {
        gameScreen.game.batch.end();
        gameScreen.game.pauseBatch.begin();

        for (int i = 0; i < cloudGroup.size; i++) {
            spriteBatch.draw(cloudGroup.get(i).sprite, cloudGroup.get(i).sprite.getX(), cloudGroup.get(i).sprite.getY() - (gameScreen.cam.position.y * cloudGroup.get(i).distanceRatio));
        }
        gameScreen.game.pauseBatch.end();
        gameScreen.game.batch.begin();
    }
}
