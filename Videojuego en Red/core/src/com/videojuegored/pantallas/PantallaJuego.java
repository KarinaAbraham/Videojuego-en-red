package com.videojuegored.pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.videojuegored.game.MyGame;
import com.videojuegored.global.Configuracion;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Arma;
import com.videojuegored.items.Item;
import com.videojuegored.mecanicas.MyInput;
import com.videojuegored.mecanicas.WorldContactListener;
import com.videojuegored.mecanicas.MyInput.Movimiento;
import com.videojuegored.niveles.Nivel;
import com.videojuegored.personajes.Persona.Estado;
import com.videojuegored.recursos.Recursos;
import com.videojuegored.red.WorldState;

public class PantallaJuego extends PantallaBase {
	
	
	private OrthographicCamera camara; 
	private Nivel nivel; 
	private OrthogonalTiledMapRenderer tmr; 
//	private Box2DDebugRenderer b2dr; 
	private boolean victoria;
	private float finalPantalla;
	private Skin skin;
	private Stage stage;
	public static boolean pausa = false;
	public static boolean cambiarPantalla = false;
	
	public PantallaJuego(MyGame myGame)	{
		super(myGame);
	}
		
	@Override
	public void show(){
		stage = new Stage();
		finalPantalla = Configuracion.ANCHO / OtrasConstantes.PPM;
		victoria = false; 
		eliminarBodys(); 
		instanciarNivel(); 
		
		if(!myGame.esSegundoJugador() && myGame.esPartidaMultiplayer()){ 
			WorldState worldState = new WorldState();
			
			worldState.setJugadorRed(myGame.getJugador());
			worldState.setSoldadoEnemigo(this.nivel.getSoldadoEnemigo());
			myGame.getWorldState().add(worldState);
		}
		
		tmr = new OrthogonalTiledMapRenderer(this.nivel.getMapa(), 1  / OtrasConstantes.PPM);  
		
		camara = new OrthographicCamera();
		Gdx.input.setInputProcessor(myGame.getMyInputListener());  
		
		myGame.getWorld().setContactListener(new WorldContactListener());	 
		pausa = false;
	
			
	}
	
	@Override
	public void render(float delta){
			
			
	if(!pausa){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 

		myGame.getWorld().step(delta, 6, 2); 

		myGame.getHud().update();

		if(!myGame.esPartidaMultiplayer() || !myGame.esSegundoJugador()){ 
	
			manejarEntradas();

			for(int i=0; i<myGame.getJugador().length; i++){
				if(myGame.getJugador(i)!=null){ 

					if(myGame.getJugador(i).getEstado()==Estado.CORRER){ 
						
						if(myGame.getJugador(i).getMiraDerecha() &&   myGame.getJugador(i).getBody().getLinearVelocity().x <= 2){ 
							myGame.getJugador(i).getBody().applyLinearImpulse(new Vector2(.1f, 0f) , myGame.getJugador(i).getBody().getWorldCenter(), true);
						
						}else if(myGame.getJugador(i).getBody().getLinearVelocity().x >= -2)
									myGame.getJugador(i).getBody().applyLinearImpulse(new Vector2(-.1f, 0f) , myGame.getJugador(i).getBody().getWorldCenter(), true);
									
					}else{
						if(myGame.getJugador(i).getEstado() == Estado.SALTAR && !myGame.getJugador(i).estaSaltando()){
							myGame.getJugador(i).getBody().applyLinearImpulse(new Vector2(0f, 2f) , myGame.getJugador(i).getBody().getWorldCenter(), true);
	
						}
	
					}					
				 
	
				}
			}
		}else{
			manejarMultiplayer();
		}
		

		for(int i=0; i<myGame.getJugador().length;i++){
			if(myGame.getJugador(i)!=null){

			myGame.getJugador(i).update(delta);
			
			if(myGame.getJugador(i).llegoFinal()){ 
				this.victoria = true; 
			
			}else{ 
				this.victoria = false;
				
			}
			}
			
		}

				if(this.victoria){
					// si victoria = true, se pasa de nivel y se recarga la pantalla para que se vuelva a inicializar todo (porque no hay nivel 2)
					myGame.pasarNivel();
					myGame.setScreen(this);
				
				}

				
				
			if(myGame.getJugador(0).getBody().getPosition().x >= finalPantalla - 1 
					&& (!myGame.esPartidaMultiplayer() || (myGame.getJugador(0).getBody().getPosition().x - myGame.getJugador(1).getBody().getPosition().x)<=2)){	

				camara.position.x += 5;
				finalPantalla += 5;	 
				
				
			} else{				

				if(myGame.getJugador(0).getBody().getPosition().x > finalPantalla){
					myGame.getJugador(0).getBody().setTransform(new Vector2(finalPantalla, myGame.getJugador(0).getBody().getPosition().y), myGame.getJugador(0).getBody().getAngle());
				
				}
				
				if(myGame.getJugador(1) != null)
				{
					if(myGame.getJugador(1).getBody().getPosition().x > finalPantalla){
				
					
					myGame.getJugador(1).getBody().setTransform(new Vector2(finalPantalla, myGame.getJugador(1).getBody().getPosition().y), myGame.getJugador(1).getBody().getAngle());
				
					}
				}
			

				if((int) myGame.getJugador(0).getBody().getPosition().x <= (int)(finalPantalla - Configuracion.ANCHO /OtrasConstantes.PPM) 
						&& (!myGame.esPartidaMultiplayer() || (myGame.getJugador(1).getBody().getPosition().x - myGame.getJugador(0).getBody().getPosition().x)<=2)){ 
			
					
				camara.position.x -= 5;
				finalPantalla -= 5;	
				}else{
					if(myGame.getJugador(0).getBody().getPosition().x < (finalPantalla - Configuracion.ANCHO /OtrasConstantes.PPM)){		
						myGame.getJugador(0).getBody().setTransform(new Vector2((finalPantalla - Configuracion.ANCHO /OtrasConstantes.PPM), myGame.getJugador(0).getBody().getPosition().y), myGame.getJugador(0).getBody().getAngle());

					}
					if(myGame.getJugador(1)!=null)
					if(myGame.getJugador(1).getBody().getPosition().x < (finalPantalla - Configuracion.ANCHO /OtrasConstantes.PPM)){
						myGame.getJugador(1).getBody().setTransform(new Vector2((finalPantalla - Configuracion.ANCHO /OtrasConstantes.PPM), myGame.getJugador(1).getBody().getPosition().y), myGame.getJugador(1).getBody().getAngle());

					}
				}
			}
			
		
		

		
		tmr.setView(camara);	
		camara.update(); 
		myGame.getSB().setProjectionMatrix(camara.combined);

		tmr.render();
	//	b2dr.render(myGame.getWorld(), camara.combined);

		myGame.getSB().begin(); 
		
			if(!this.nivel.getSoldadoEnemigo().isEmpty()) 
			for(int i = 0; i<this.nivel.getSoldadoEnemigo().size();i++){ 
			
				if(this.nivel.getSoldadoEnemigo().get(i).getPorcentajeVidaActual()>0){
					
					this.nivel.getSoldadoEnemigo().get(i).update(delta, myGame.getJugador());
					this.nivel.getSoldadoEnemigo().get(i).draw(myGame.getSB());
					
					if(this.nivel.getSoldadoEnemigo().get(i).getEstado() == Estado.QUIETO){ 
						this.nivel.setBala(	this.nivel.getSoldadoEnemigo().get(i).disparar());  
					}
							
				}
				
			}
			
			if(!myGame.esSegundoJugador() && myGame.esPartidaMultiplayer()){
				WorldState worldState = new WorldState();			
				worldState.setJugadorRed(myGame.getJugador());
				worldState.setSoldadoEnemigo(this.nivel.getSoldadoEnemigo());
	

				myGame.getWorldState().add(worldState);
		
			}

			if(!this.nivel.getBala().isEmpty()) 
				for(int i=0; i<this.nivel.getBala().size();i++){ 
					if(!this.nivel.getBala().get(i).colisionó()){ 
					
						this.nivel.getBala().get(i).update(delta); 
						if(this.nivel.getBala().get(i).getTiempoDisparo() >= 3){
							
							this.nivel.getBala().get(i).colisiona(); 

						}		
						else{
							
							  this.nivel.getBala().get(i).draw(myGame.getSB());
						}

					}else{ 

						this.nivel.getBala().get(i).eliminarBody(); 
						this.nivel.getBala().remove(i); 
						}
		
			}
			for(int i = 0; i<myGame.getJugador().length; i++){ 
				if(myGame.getJugador(i)!=null){
				
					if(myGame.getJugador(i).getPorcentajeVidaActual()>0){ 
						myGame.getJugador(i).draw(myGame.getSB());
					}else{
						myGame.setScreen(this);
					}
				}
			}
		myGame.getSB().end();
		myGame.getSB().setProjectionMatrix(myGame.getHud().stage.getCamera().combined);

		myGame.getHud().stage.draw(); 

	
		}
	
		if(myGame.esPartidaMultiplayer() && myGame.seCayoLaConexion()){
			Gdx.input.setInputProcessor(stage); 
			skin  = new Skin(Gdx.files.internal(Recursos.SKIN)); 
			pausa = true;
			final Dialog dialog = new Dialog("CONEXION CAIDA", skin);
			dialog.text("Se corto la conexion");
			TextButton botonOk = new TextButton("OK", skin); 
			botonOk.addListener(new ClickListener(){
			
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						cambiarPantalla = true;
						dialog.hide();
						pausa = false;
					} 
					
				});
			dialog.add(botonOk);
			dialog.layout();
			dialog.setPosition(camara.position.x, camara.position.y);

			dialog.show(stage);
			if(cambiarPantalla) myGame.create();
			stage.draw();
			
		}
			
		}
	
	
	
	@Override
	public void resize(int width, int height){
		camara.setToOrtho(false, width / OtrasConstantes.PPM, height / OtrasConstantes.PPM);	
	}
	
	@Override
	public void dispose(){
		
	//	b2dr.dispose();
		tmr.dispose();
		this.nivel.getMapa().dispose();
		skin.dispose();
	}
	
	private void manejarEntradas(){
		if(!myGame.getEntrada().isEmpty()){ 
			MyInput input = (MyInput) myGame.getEntrada().poll();
		
			if(input.movimiento == Movimiento.DERECHA){ 

				myGame.getJugador(input.numeroJugador).setEstado(Estado.CORRER);
				myGame.getJugador(input.numeroJugador).miraDerecha(true); 
				
			}else if (input.movimiento == Movimiento.IZQUIERDA){
				myGame.getJugador(input.numeroJugador).setEstado(Estado.CORRER);
				myGame.getJugador(input.numeroJugador).miraDerecha(false);	
			
			}else if(input.movimiento == Movimiento.ARRIBA){
				myGame.getJugador(input.numeroJugador).setEstado(Estado.SALTAR);

			}else if(input.movimiento == Movimiento.QUIETO){
					myGame.getJugador(input.numeroJugador).setEstado(Estado.QUIETO);

				}
				
			if(input.movimiento == Movimiento.DISPARAR){
				this.nivel.setBala(myGame.getJugador(input.numeroJugador).disparar());
				
		
			}
		}
	}
	
	private void manejarMultiplayer(){
		if(!myGame.getWorldState().isEmpty()){
	
			WorldState worldState = myGame.getWorldState().poll();
			for(int i = 0; i < worldState.getSoldadoEnemigoRed().size(); i++){
				
				for(int j = 0; j < nivel.getSoldadoEnemigo().size(); j++){
	
					if(nivel.getSoldadoEnemigo().get(j).getId() == worldState.getSoldadoEnemigoRed().get(i).id){
						
						nivel.getSoldadoEnemigo().get(j).setVidaActual(worldState.getSoldadoEnemigoRed().get(i).vidaActual);
						nivel.getSoldadoEnemigo().get(j).setTiempoDisparo(worldState.getSoldadoEnemigoRed().get(i).tiempoDisparo);
						nivel.getSoldadoEnemigo().get(j).setTiempoEstado(worldState.getSoldadoEnemigoRed().get(i).tiempoEstado);
						nivel.getSoldadoEnemigo().get(j).setPuedeDisparar(worldState.getSoldadoEnemigoRed().get(i).puedeDisparar);
						nivel.getSoldadoEnemigo().get(j).miraDerecha(worldState.getSoldadoEnemigoRed().get(i).miraDerecha);
						nivel.getSoldadoEnemigo().get(j).setEstado(worldState.getSoldadoEnemigoRed().get(i).estado);
						nivel.getSoldadoEnemigo().get(j).setEstadoAnterior(worldState.getSoldadoEnemigoRed().get(i).estadoAnterior);
						nivel.getSoldadoEnemigo().get(j).setVelocidad(worldState.getSoldadoEnemigoRed().get(i).velocidad);
						break;
					}
				}
				
			}
			
			for(int i = 0; i < nivel.getJugador().length; i++){					
				nivel.getJugador()[i].setVidaActual(worldState.getJugadorRed()[i].vidaActual);
				nivel.getJugador()[i].setTiempoDisparo(worldState.getJugadorRed()[i].tiempoDisparo);
				nivel.getJugador()[i].setTiempoEstado(worldState.getJugadorRed()[i].tiempoEstado);
				nivel.getJugador()[i].setPuedeDisparar(worldState.getJugadorRed()[i].puedeDisparar);
				nivel.getJugador()[i].miraDerecha(worldState.getJugadorRed()[i].miraDerecha);
				nivel.getJugador()[i].setEstado(worldState.getJugadorRed()[i].estado);
				nivel.getJugador()[i].setEstadoAnterior(worldState.getJugadorRed()[i].estadoAnterior);
				nivel.getJugador()[i].setArma(worldState.getJugadorRed()[i].armaActual);
				nivel.getJugador()[i].getBody().setTransform(new Vector2(worldState.getJugadorRed()[i].posicionX,  worldState.getJugadorRed()[i].posicionY), nivel.getJugador()[i].getBody().getAngle());
			}
			
		

		}
	}
	private void instanciarNivel(){ 
		
		switch (myGame.getNivelActual()){	
			case 1:{
				
					
					TiledMap tiledMap = new TmxMapLoader().load(Recursos.NIVEL_UNO_MAPA);
					
					this.nivel = new Nivel("NIVEL 1",  myGame.getWorld());
					this.nivel.setMapa(tiledMap);
					this.nivel.setSuelo("Piso");
					this.nivel.setArmaDisponible(new Arma("Arma x2", 100, Recursos.TEXTURA_BALA,  20));
					this.nivel.setItem(new Item("Poción de vida", 100));
					this.nivel.setCajaArma("Caja Arma");
					this.nivel.setCajaItem("Caja Poción");
					this.nivel.setPlataforma("Plataforma");
					this.nivel.setTecho("Techo");
					this.nivel.setPared("Pared");
					this.nivel.setSoldadoEnemigo("Soldados Enemigos");
					this.nivel.setLimitesEnemigos("Límites");
					this.nivel.setPuertaFinal("Puerta Final");
					this.nivel.setJugadores(myGame.getJugador());
			    }		
				break;
			case 2:
				{

				// Acá iría lo necesario para el nivel 2	
				TiledMap tiledMap = new TmxMapLoader().load(Recursos.NIVEL_UNO_MAPA);
				
				this.nivel = new Nivel("NIVEL 2",  myGame.getWorld());
				this.nivel.setMapa(tiledMap);
				this.nivel.setSuelo("Piso");
				this.nivel.setArmaDisponible(new Arma("Arma x2", 100, Recursos.TEXTURA_BALA,  20));
				this.nivel.setItem(new Item("Poción de vida", 100));
				this.nivel.setCajaArma("Caja Arma");
				this.nivel.setCajaItem("Caja Poción");
				this.nivel.setPlataforma("Plataforma");
				this.nivel.setTecho("Techo");
				this.nivel.setPared("Pared");
				this.nivel.setSoldadoEnemigo("Soldados Enemigos");
				this.nivel.setLimitesEnemigos("Límites");
				this.nivel.setPuertaFinal("Puerta Final");
				}
				break;
			case 3: 
				nivel = new Nivel("NIVEL 3", myGame.getWorld());
				
				break;
				
		
		}
	
	}	
	private void eliminarBodys(){ 
		if(nivel!=null){
			
  			if(!this.nivel.getBala().isEmpty()){
					
					for(int i = 0; i < this.nivel.getBala().size(); i++){
						
		
						this.nivel.getBala().get(i).eliminarBody();
						this.nivel.getBala().remove(i);
					}
					
				}
				
				if(!this.nivel.getElementoEscenario().isEmpty()){
					
					for(int i = 0; i < this.nivel.getElementoEscenario().size(); i++){						
		
					    this.nivel.getElementoEscenario().get(i).eliminarBody();
					}
					
				}

				for(int i=0;i<myGame.getJugador().length;i++){
					if(myGame.getJugador(i)!=null){
						
						myGame.getWorld().destroyBody(myGame.getJugador(i).getBody());
						myGame.getJugador(i).setBody(100, 100, 40, 40);					
						myGame.getJugador(i).inicializar();
						
					}
				}
				
				if(!this.nivel.getSoldadoEnemigo().isEmpty()){
					for(int i = 0; i < this.nivel.getSoldadoEnemigo().size(); i++){
						
					    this.nivel.getSoldadoEnemigo().get(i).eliminarBody();
					}
				}
		
		}
		
	}
}
