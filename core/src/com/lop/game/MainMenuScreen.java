package com.lop.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;

public class MainMenuScreen implements Screen{
	private MyGame game;
	public boolean render = true;
	public MainMenuScreen(MyGame myGame) {
		game = myGame;
		
		game.getInputs().clear();
		Controllers.addListener(new MenuListener(this));
	}

	@Override
	public void render(float delta) {
		if(render)
			game.batch.draw(game.img, 0, 0);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void start() {
		game.setScreen(new GameScreen(game));
	}

}
