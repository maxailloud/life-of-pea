package com.lop.game;

import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen{
	private MyGame game;
	public MainMenuScreen(MyGame myGame) {
		game = myGame;
		
		game.getInputs().clear();
		//game.getInputs().addProcessor(new MenuListener(this));
	}

	@Override
	public void render(float delta) {
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

}
