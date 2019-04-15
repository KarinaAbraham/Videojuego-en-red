package com.videojuegored.personajes;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Arma;
import com.videojuegored.items.Bala;

public class SoldadoEnemigo extends Persona{
	private Vector2 velocidad = new Vector2(1f,0); 
	private int id;
	public SoldadoEnemigo(int idEnemigo, int vida, Arma armaInicial, World world, TextureAtlas atlas, String nombreIAtlas){
		super(vida, armaInicial, world, atlas, nombreIAtlas, false); 
		estado = Estado.CORRER; 
		this.id	=	idEnemigo;
	}
	
	@Override
	public void recibirDisparo(Bala bala){ 
	super.recibirDisparo(bala); 
	if(vidaActual <= 0) setCategoryFilter(OtrasConstantes.BIT_NO_COLISIONABLE);  
	}
	
	private void setCategoryFilter(short filterBit){ 
		Filter filter	=	new	Filter(); 
		filter.categoryBits	=	filterBit; 
		fixture.setFilterData(filter); 
	}
	public void setBody(Rectangle objeto){

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;			
		bodyDef.position.set(((objeto.getX() + objeto.getWidth() / 2) / OtrasConstantes.PPM) , ((objeto.getY() + objeto.getHeight() / 2) / OtrasConstantes.PPM) );

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(objeto.getWidth() / 2 / OtrasConstantes.PPM,  objeto.getHeight() / 2 / OtrasConstantes.PPM);
		fixtureDef.shape = shape;

		fixtureDef.filter.categoryBits = OtrasConstantes.BIT_ENEMIGO;
		fixtureDef.filter.maskBits =  OtrasConstantes.BIT_SUPERFICIE | OtrasConstantes.BIT_PARED | OtrasConstantes.BIT_BALA | OtrasConstantes.BIT_LIMITE_ENEMIGO;
	
		body = world.createBody(bodyDef);
		fixture	= body.createFixture(fixtureDef);
		fixture.setUserData(this);


		 
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

		
	}
		
	public void invertirVelocidad(boolean x, boolean y){ 
		if(x){			
			velocidad.x = - velocidad.x; 
			if(velocidad.x>0){
				miraDerecha	= true;
			}else{
				miraDerecha	= false;
			}
		}
		if(y){
			velocidad.y = - velocidad.y;
	
		}

	}
	public void update(float dt, Jugador[] jugador){   
	
		if(this.vidaActual>0){
			if(!puedeDisparar){
				tiempoDisparo+=dt;
				
				if(tiempoDisparo>=.5f){

					puedeDisparar= true;
					tiempoDisparo = 0;
				}
			}
			
			setRegion(getFrames(dt)); 							
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2); 
						
			verificarDisparo(jugador);

		}
		
	}
	private void verificarDisparo(Jugador[] jugador){
		boolean correr = true;
		for(int i = 0; i < jugador.length; i++){
			if(jugador[i]!=null){ 
				
				if((int)(jugador[i].getBody().getPosition().y - jugador[i].getHeight()) == (int) (body.getPosition().y - this.getHeight())){
					
				
					if((jugador[i].getBody().getPosition().x - body.getPosition().x >= -500 / OtrasConstantes.PPM 
							&&jugador[i].getBody().getPosition().x - body.getPosition().x <=0
							  && !miraDerecha)
						|| (jugador[i].getBody().getPosition().x - body.getPosition().x <= 500 / OtrasConstantes.PPM 
									&&jugador[i].getBody().getPosition().x - body.getPosition().x >=0
									  && miraDerecha)){ 
									estadoAnterior = estado;
									estado = Estado.QUIETO;
									correr = false;
					
						
					}
					
				}
			
			}
		} if(correr){
			estadoAnterior = estado;
			estado = Estado.CORRER;
		}

		
	}
	@Override
	protected TextureRegion getFrames(float dt){
		TextureRegion region;
		
		if(super.estado == Estado.CORRER){
			region	=	animationCorrer.getKeyFrame(tiempoEstado, true);
			body.setLinearVelocity(velocidad); 

		}else{
			region	=   animationQuieto.getKeyFrame(tiempoEstado, true);
			
		}

		if(( !miraDerecha && !region.isFlipX()) ||  (miraDerecha && region.isFlipX())){
				region.flip(true, false);
			}
		
		tiempoEstado = estado == estadoAnterior ? tiempoEstado + dt : 0;		 
		estadoAnterior = estado;
		return region;
	}	
	
	public void eliminarBody(){ 
		if(body!=null) world.destroyBody(body);
	}

	
	public void setVelocidad(Vector2 velocidad){
		this.velocidad = velocidad;
	}
	
	public int getId(){return id;}
	public Vector2 getVelocidad(){return velocidad;}
}
