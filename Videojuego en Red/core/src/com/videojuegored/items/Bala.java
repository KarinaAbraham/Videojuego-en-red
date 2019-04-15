package com.videojuegored.items;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public class Bala extends Sprite implements Serializable {
 
	private TextureRegion regionBala;
	private boolean	colisionó	=	false;
	private	Body body;
	private World world;
	private float daño;
	private boolean disparadaPorJugador; 
	private float tiempoDisparo = 0; 
	
	public Bala(Texture textura, World world, boolean disparadaPorJugador, float daño){
		regionBala	=	new	TextureRegion(textura);
		setRegion(regionBala);
		this.world = world;
		this.daño = daño;
		this.disparadaPorJugador	=	disparadaPorJugador;
		setBounds(0,0, 10  / OtrasConstantes.PPM, 10  / OtrasConstantes.PPM); 
	}
		
	public void colisiona(){ 
		colisionó	=	true;
		
	}
	
	public void update(float dt){
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		
		tiempoDisparo += dt; 
	}
	
	
	public void setBody(int x, int y, int width, int height){ 
		 BodyDef bodyDef	= new BodyDef();
		 bodyDef.position.set(x  / OtrasConstantes.PPM, y / OtrasConstantes.PPM);
		 bodyDef.type	= BodyDef.BodyType.DynamicBody; 		 
		 body = world.createBody(bodyDef);	 
		 PolygonShape shape		= new PolygonShape();
		 shape.setAsBox(width / OtrasConstantes.PPM, height / OtrasConstantes.PPM);	    
		 FixtureDef fixtureDef  = new FixtureDef();
		 fixtureDef.shape  = shape;
		 fixtureDef.filter.categoryBits = OtrasConstantes.BIT_BALA; 
		 
		 if(disparadaPorJugador){ 
			 fixtureDef.filter.maskBits 	= OtrasConstantes.BIT_ENEMIGO | OtrasConstantes.BIT_SUPERFICIE | OtrasConstantes.BIT_PARED;

		 }else{
			 fixtureDef.filter.maskBits 	= OtrasConstantes.BIT_JUGADOR | OtrasConstantes.BIT_SUPERFICIE | OtrasConstantes.BIT_PARED;
		 }
		 fixtureDef.isSensor = true; 
		 body.createFixture(fixtureDef).setUserData(this);
 		 setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2); 
	
	}
	
	public void eliminarBody(){ 
		if(body!=null) world.destroyBody(body);
		
	}
	
	public boolean colisionó(){return colisionó;}
	public Body	getBody(){return body;}
	public boolean disparóUnJugador(){return disparadaPorJugador;}
	public float getDaño(){return daño;}
	public float getTiempoDisparo(){return tiempoDisparo;}
	
	
	
	
}
