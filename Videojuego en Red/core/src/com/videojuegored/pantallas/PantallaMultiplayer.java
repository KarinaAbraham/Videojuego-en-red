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
import com.videojuegored.recursos.Recursos;

public class PantallaMultiplayer extends PantallaBase{

	private Texture texturaFondo;
	private TextureRegion regionFondo;
	private Stage escenario;
	private Skin skin;
	
	public PantallaMultiplayer(MyGame myGame){

		super(myGame);		

	}
	@Override 
	public void show() {

		skin 	  = new Skin(Gdx.files.internal(Recursos.SKIN)); 


		texturaFondo = new Texture(Recursos.FONDO_MENU_PRINCIPAL); 
		regionFondo  = new TextureRegion(texturaFondo); 
		escenario = new Stage(); 

		Table tabla = new Table(skin); 
		tabla.setFillParent(true);  

		TextButton btnCrearPartida = new  TextButton("CREAR PARTIDA", skin); 
		btnCrearPartida.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);				
				


				myGame.setMultiplayer(null);
				
				myGame.setScreen(myGame.getPantallaJuego()); 
				
			}
			
		});
		
		tabla.add(btnCrearPartida).width(200).height(30).row(); 

		

		TextButton btnUnirsePartida = new  TextButton("ENTRAR A PARTIDA", skin); 
		btnUnirsePartida.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				myGame.setScreen(myGame.getPantallaUnirseAPartida());
			} 
			
		});
	
		
		tabla.add(btnUnirsePartida).width(200).height(30).row();		
		
		

		TextButton btnVolver = new  TextButton("VOLVER", skin);

		btnVolver.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				myGame.setScreen(myGame.getPantallaMenuPrincipal());
			} 
			
		});
		
		tabla.add(btnVolver).width(200).height(30).row();		
				
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
