package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class Ground {
    private Array<Sprite> groundSprites = new Array<>();
	private Sprite littleBushSprite;
	private Sprite bigBushSprite;

    public Ground(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");
        littleBushSprite = spriteAtlas.createSprite("little_bush");
        bigBushSprite = spriteAtlas.createSprite("big_bush");

        for (int i = 0; i < 22; i++) {
            groundSprites.add(groundSprite);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < groundSprites.size; i++) {
            spriteBatch.draw(groundSprites.get(i), -21 + (1.95f * i), 0, 2f, 2f);
        }
        spriteBatch.draw(littleBushSprite, -21 + 3, 2, 2f, 2f);
        spriteBatch.draw(littleBushSprite, -21 + 6, 2, 2f, 2f);
        spriteBatch.draw(littleBushSprite, -21 + 11, 2, 2f, 2f);
        spriteBatch.draw(bigBushSprite, -21 + 18, 2, 6f, 2f);
        spriteBatch.draw(littleBushSprite, -21 + 22, 2, 2f, 2f);
        spriteBatch.draw(littleBushSprite, -21 + 25, 2, 2f, 2f);
        spriteBatch.draw(littleBushSprite, -21 + 33, 2, 2f, 2f);
    }
}
