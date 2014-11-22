package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Ground implements Renderable{
	private Sprite[] groundSprites = new Sprite[21];

    public Ground(TextureAtlas spriteAtlas) {
        Sprite sprite = spriteAtlas.createSprite("slice03");

        for (int i = 0; i < groundSprites.length; i++) {
            groundSprites[i] = sprite;
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < groundSprites.length; i++) {
            spriteBatch.draw(groundSprites[i], -21 + (2 * i), 0, 2f, 2f);
        }
    }
}
