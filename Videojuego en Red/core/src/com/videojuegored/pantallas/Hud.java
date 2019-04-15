package com.videojuegored.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.videojuegored.game.MyGame;
import com.videojuegored.global.Configuracion;
import com.videojuegored.recursos.Recursos;

public class Hud{
	public Stage stage;
	private Viewport viewport;
	private Skin skin;
	private MyGame	myGame;
	
	private Label lblNumJ1, lblNumJ2, lblNombreArmaJ1, lblCantBalasJ1, lblNombreArmaJ2, lblCantBalasJ2, lblPVJ1, lblPVJ2; 
	public Hud(MyGame myGame){
		
		this.myGame	=	myGame;
		viewport	=	new FitViewport(Configuracion.ANCHO, Configuracion.ALTO, new OrthographicCamera());
		stage	=	new Stage(viewport, myGame.getSB());
		skin 	  = new Skin(Gdx.files.internal(Recursos.SKIN));

		Table table	=	new	Table(skin);
		table.top();
		table.setFillParent(true);
		
		lblNumJ1		=	new Label("JUGADOR 1", skin);
		lblNombreArmaJ1	=   new Label(myGame.getJugador(0).getArmaActual().getNombreArma(), skin);
		
		lblCantBalasJ1 	=	new Label(myGame.getJugador(0).getArmaActual().getBalasActuales() + " balas", skin);
		lblPVJ1			=	new Label(myGame.getJugador(0).getPorcentajeVidaActual() + " %", skin);
		
		
		if(myGame.getJugador(1)!=null){
			lblNumJ2		= 	new Label("JUGADOR 2", skin);
			lblNombreArmaJ2	=   new Label(myGame.getJugador(1).getArmaActual().getNombreArma(), skin);
			lblCantBalasJ2	=	new Label(myGame.getJugador(1).getArmaActual().getBalasActuales() + " balas", skin);
			lblPVJ2			=	new Label(myGame.getJugador(1).getPorcentajeVidaActual() + " %", skin);
			
				
		
		}
	
		table.add(lblNumJ1).expandX().padTop(10);
		if (lblNumJ2!=null) table.add(lblNumJ2).expandX().padTop(10);
		table.add(lblNombreArmaJ1).expandX().padTop(10);
		if(lblNombreArmaJ2!=null) table.add(lblNombreArmaJ2).expandX().padTop(10);

		table.row();
		
		table.add(lblPVJ1).expandX();
		if(lblPVJ2!=null) table.add(lblPVJ2).expandX();
		table.add(lblCantBalasJ1).expandX();
		if(lblCantBalasJ2!=null)  	table.add(lblCantBalasJ2).expandX();

		stage.addActor(table);
		
		
	}
	
	
	public void update(){
		lblNombreArmaJ1.setText(myGame.getJugador(0).getArmaActual().getNombreArma());
		lblPVJ1.setText(myGame.getJugador(0).getPorcentajeVidaActual() + " %");

		if(myGame.getJugador(0).getArmaActual().getBalasActuales() > 0){
			lblCantBalasJ1.setText(myGame.getJugador(0).getArmaActual().getBalasActuales() + " balas");

		}else{
			lblPVJ1.setText(myGame.getJugador(0).getPorcentajeVidaActual() + " %");
			lblCantBalasJ1.setText("balas infinitas");

		}
		
		if(myGame.getJugador(1)!=null){
			lblNombreArmaJ2.setText(myGame.getJugador(1).getArmaActual().getNombreArma());
			lblPVJ2.setText(myGame.getJugador(1).getPorcentajeVidaActual() + " %");

			if(myGame.getJugador(1).getArmaActual().getBalasActuales() > 0){
				lblCantBalasJ2.setText(myGame.getJugador(1).getArmaActual().getBalasActuales() + " balas");

			}else{
				lblPVJ2.setText(myGame.getJugador(1).getPorcentajeVidaActual() + " %");
				lblCantBalasJ2.setText("balas infinitas");

			}
		}
	}
	
	
	
}
