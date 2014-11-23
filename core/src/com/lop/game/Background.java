package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import java.util.Map;
import java.util.TreeMap;

public class Background {
	private TreeMap<Float, Cloud> cloudTreeMap = new TreeMap<Float, Cloud>();

    public Background(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");

        for (int i = 0; i < 20; i++) {
            Cloud cloud = new Cloud(spriteAtlas.createSprite("cloud" + MathUtils.random(1, 3)));
            cloud.sprite.setSize(129f, 71f);
            initCloud(cloud, i);
            cloudTreeMap.put(cloud.distanceRatio, cloud);
        }
    }

    public void initCloud(Cloud cloud, int index) {
        cloud.distanceRatio = MathUtils.random(0.2f, 1.4f);
        cloud.sprite.setPosition(
                MathUtils.random(0f + cloud.sprite.getWidth() / 2,
                        864f - cloud.sprite.getHeight() / 2), MathUtils.random(150f, 840f) + (10 * cloud.distanceRatio)
        );
        cloud.sprite.setScale(cloud.distanceRatio);
    }

    public void render(GameScreen gameScreen) {
        gameScreen.game.batch.end();
        gameScreen.game.pauseBatch.begin();

        for(Map.Entry<Float, Cloud> entry : cloudTreeMap.entrySet()) {
            Float distanceRatio = entry.getKey();
            Cloud cloud = entry.getValue();

            float yDelta = (gameScreen.cam.position.y * (distanceRatio * 2));
            cloud.sprite.setY(cloud.sprite.getY() - yDelta);
            cloud.sprite.draw(gameScreen.game.pauseBatch);

            cloud.sprite.setY(cloud.sprite.getY() + yDelta);
        }
        gameScreen.game.pauseBatch.end();
        gameScreen.game.batch.begin();
    }
}
