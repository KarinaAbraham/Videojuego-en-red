package com.videojuegored.mecanicas;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.videojuegored.game.MyGame;


public class MyClickListener extends ClickListener{
	private TextField textField;
	private MyGame myGame;
	
	@Override 
	public void clicked(InputEvent event, float x, float y) {
		super.clicked(event, x, y);

		if(myGame!=null){
			
			if(textField!=null && !textField.getText().equals("")){
					InetAddress ip = null;
					try {
						ip = InetAddress.getByName(textField.getText()); 
	
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					myGame.setMultiplayer(ip);

					while(!myGame.seConecto()){ 
						if(myGame.seCayoLaConexion()){ 
							break;
						}
					}
					if(!myGame.seCayoLaConexion()){		
						
      					myGame.setScreen(myGame.getPantallaJuego()); 
					
					} else{
						myGame.create(); 
					}
			} 
			
		}	
		
	} 
	
	public void setMyGame(MyGame myGame){ 
		this.myGame = myGame;
	}
	
	public void setTextField(TextField textField){
		this.textField = textField;
	}
	
}
