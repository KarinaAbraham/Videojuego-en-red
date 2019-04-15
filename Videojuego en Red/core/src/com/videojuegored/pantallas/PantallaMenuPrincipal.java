package com.videojuegored.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.videojuegored.game.MyGame;
import com.videojuegored.recursos.*;

public class PantallaMenuPrincipal extends PantallaBase{
	
	private Texture texturaFondo;
	private TextureRegion regionFondo;
	private Stage escenario;
	private Skin skin;
	
	public PantallaMenuPrincipal(MyGame myGame){
		super(myGame);
	
	}
	
	@Override 
	public void show() {

		texturaFondo = new Texture(Recursos.FONDO_MENU_PRINCIPAL); 
		regionFondo  = new TextureRegion(texturaFondo); 
		skin 	  = new Skin(Gdx.files.internal(Recursos.SKIN)); 
		escenario = new Stage();
		
		Table tabla = new Table(skin); 
		tabla.setFillParent(true); 
		
		TextButton btnSinglePlayer = new  TextButton("SINGLEPLAYER", skin); 
		btnSinglePlayer.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);				
				myGame.setScreen(myGame.getPantallaJuego());
				
			} 
			
		});
		
		tabla.add(btnSinglePlayer).width(200).height(30).row();
		

		TextButton btnMultiPlayer = new  TextButton("MULTIPLAYER", skin); 
		
		btnMultiPlayer.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);				
				myGame.setScreen(myGame.getPantallaMultiplayer());
			} 
			
		});
		
		tabla.add(btnMultiPlayer).width(200).height(30).row();		
		
		

		TextButton btnSalir = new  TextButton("SALIR", skin);

		btnSalir.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				 Gdx.app.exit(); 
			} 
			
			
		});
		
		tabla.add(btnSalir).width(200).height(30).row();		
				
		escenario.addActor(tabla); 
	
		Gdx.input.setInputProcessor(escenario);

		
		
	}
	
	@Override
	public void render(float delta){	

		myGame.getSB().begin();
		myGame.getSB().draw(regionFondo, 0,0); 
		myGame.getSB().end();
		
		
		escenario.draw();
		
		
	}
	
	
	@Override
	public void dispose()
	{
		escenario.dispose();
		texturaFondo.dispose();
		skin.dispose();
	}
	

}
