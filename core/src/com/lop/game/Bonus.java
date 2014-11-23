package com.lop.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

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
    public void bonus(Player player){
    	Fixture fix = player.getBody().getFixtureList().get(0);
    	switch(type){
    		case "starBronze":
		    	
				fix.setDensity(fix.getDensity() * 2);
				player.scale(1.01f);
				player.getBody().resetMassData();
				break;
    		case "starSilver":
		    	fix.setDensity(fix.getDensity() * 10);
				player.scale(1.01f);
				player.getBody().resetMassData();
				break;
	    
    	
    	}
		toBeDestroy = true;
    }
}
