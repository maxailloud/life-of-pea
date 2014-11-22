package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Ground implements Renderable{
	private Sprite[] groundSprites = new Sprite[21];
	private Sprite littleBushSprite;
	private Sprite bigBushSprite;

    public Ground(TextureAtlas spriteAtlas) {
        Sprite groundSprite = spriteAtlas.createSprite("slice03");
        littleBushSprite = spriteAtlas.createSprite("little_bush");
        bigBushSprite = spriteAtlas.createSprite("big_bush");

        for (int i = 0; i < groundSprites.length; i++) {
            groundSprites[i] = groundSprite;
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < groundSprites.length; i++) {
            spriteBatch.draw(groundSprites[i], -21 + (2 * i), 0, 2f, 2f);
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
