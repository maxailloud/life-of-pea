package com.lop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

public class KeyboardListener implements InputProcessor {

	public MyGame myGame;

	public KeyboardListener(MyGame myGame) {
		this.myGame = myGame;
	}
	public Array<Player> getPlayers() {
		return myGame.gameScreen.getPlayers();
	}
	public void updateMove(){
		boolean move = false;
		float value = 0f;
		if(Gdx.input.isKeyPressed(Keys.Q)){
			move = true;
			value = -1f;
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			move = true;
			value = 1f;
		}
			
		if(move){
			
			if(Math.abs(value) >= 0.25f){
				Player player = getPlayers().get(0);
				if(!player.isDead()){
					player.move(value);
				}
			}

		}
	}
	@Override
	public boolean keyDown(int keycode) {
		if (!myGame.gameScreen.playerWin) {
			if(keycode == Keys.Z)
			{
				Player player = getPlayers().get(0);
				if(!player.isDead()){
					float value = 0f;
					if(Gdx.input.isKeyPressed(Keys.Q)){
						value = -0.5f;
					}
					if(Gdx.input.isKeyPressed(Keys.D)){
						value = 0.5f;
					}
					player.jump(value, myGame.jumpSound);
				}
				return true;
			}
			if(keycode == Keys.A)
			{
				Player player = getPlayers().get(0);
				if(!player.isDead()){
					float value = -1f;
					player.dash(value, 0f, myGame.dashSound);
				}
				return true;
			}
			if(keycode == Keys.E)
			{
				Player player = getPlayers().get(0);
				if(!player.isDead()){
					float value = 1f;
					player.dash(value, 0f, myGame.dashSound);
				}
				return true;
			}
		}
		if(keycode == Keys.A)
		{
			
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
