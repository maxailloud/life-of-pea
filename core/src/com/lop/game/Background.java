package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import java.util.Map;
import java.util.TreeMap;

public class Background {
	private TreeMap<Float, Cloud> cloudTreeMap = new TreeMap<>();

    public Background(TextureAtlas spriteAtlas) {
        for (int i = 0; i < 20; i++) {
            Cloud cloud = new Cloud(spriteAtlas.createSprite("cloud" + MathUtils.random(1, 3)));
            cloud.sprite.setSize(129f, 71f);
            initCloud(cloud);
            cloudTreeMap.put(cloud.distanceRatio, cloud);
        }
    }

    public void initCloud(Cloud cloud) {
        cloud.distanceRatio = MathUtils.random(0.2f, 1.4f);
        cloud.sprite.setPosition(
                MathUtils.random(0f + cloud.sprite.getWidth(), 864f - cloud.sprite.getWidth()),
                MathUtils.random(150f, 840f) + (10 * cloud.distanceRatio)
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

            if(0 > cloud.sprite.getY() + cloud.sprite.getHeight()){
                initCloud(cloud);
                cloud.sprite.setY(gameScreen.cam.position.y + Gdx.graphics.getHeight() + (10 * cloud.distanceRatio));
            } else {
                cloud.sprite.setY(cloud.sprite.getY() + yDelta);
            }
        }
        gameScreen.game.pauseBatch.end();
        gameScreen.game.batch.begin();
    }
}
