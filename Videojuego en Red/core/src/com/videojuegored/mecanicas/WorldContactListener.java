package com.videojuegored.mecanicas;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.videojuegored.items.Bala;
import com.videojuegored.niveles.CajaArma;
import com.videojuegored.niveles.CajaItem;
import com.videojuegored.niveles.ElementoInteractivo;
import com.videojuegored.niveles.Límite;
import com.videojuegored.niveles.PuertaFinal;
import com.videojuegored.personajes.Jugador;
import com.videojuegored.personajes.Persona;
import com.videojuegored.personajes.SoldadoEnemigo;

public class WorldContactListener implements ContactListener{ 

	@Override
	public void beginContact(Contact contact) {
			
		
		if((Jugador.class.isInstance(contact.getFixtureA().getUserData()) && contact.getFixtureA().isSensor()) || (Jugador.class.isInstance(contact.getFixtureB().getUserData()) && contact.getFixtureB().isSensor())
			&& ((contact.getFixtureA().getUserData().equals("Superficie") || contact.getFixtureB().getUserData().equals("Superficie")))){

			Fixture	jugador	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureA()	:	contact.getFixtureB();
			((Jugador) jugador.getUserData()).cambiarEstadoSalta(false); 

			
			}else{
				verColisionCaja(contact);

			}
	}
		
	
				
	@Override
	public void endContact(Contact contact) {
		

			if((Jugador.class.isInstance(contact.getFixtureA().getUserData()) && contact.getFixtureA().isSensor()) || (Jugador.class.isInstance(contact.getFixtureB().getUserData()) && contact.getFixtureB().isSensor())
				&& ((contact.getFixtureA().getUserData().equals("Superficie") || contact.getFixtureB().getUserData().equals("Superficie")))){

				Fixture	jugador	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureA()	:	contact.getFixtureB();

				((Jugador) jugador.getUserData()).cambiarEstadoSalta(true);

			}else{
				if(Jugador.class.isInstance(contact.getFixtureA().getUserData()) || Jugador.class.isInstance(contact.getFixtureB().getUserData()) ){
					Fixture	objeto	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureB()	:	contact.getFixtureA();
					Fixture	jugador	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureA()	:	contact.getFixtureB();
					
					if(PuertaFinal.class.isInstance(objeto.getUserData())){ 
						((Jugador) jugador.getUserData()).setLlegoFinal(false); 
					}

					
					
				}
			}
		
		
	}

	
	
	private void verColisionPuertaFinal(Contact contact){
		if(Jugador.class.isInstance(contact.getFixtureA().getUserData()) || Jugador.class.isInstance(contact.getFixtureB().getUserData()) ){
		
			Fixture	objeto	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureB()	:	contact.getFixtureA();
			Fixture	jugador	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureA()	:	contact.getFixtureB();
			
			if(PuertaFinal.class.isInstance(objeto.getUserData())){
				
				((Jugador) jugador.getUserData()).setLlegoFinal(true);
			}

			
			
		}
		
	}
	
	private void verColisionCaja(Contact contact){
		if(Jugador.class.isInstance(contact.getFixtureA().getUserData()) || Jugador.class.isInstance(contact.getFixtureB().getUserData()) ){

			Fixture	objeto	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureB()	:	contact.getFixtureA();
			Fixture	jugador	=	Jugador.class.isInstance(contact.getFixtureA().getUserData()) 	?	contact.getFixtureA()	:	contact.getFixtureB();

			if(ElementoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass() ) ){
				
				((ElementoInteractivo) objeto.getUserData()).interactua();
				
				if(CajaArma.class.isInstance(objeto.getUserData())){  
					
					((Jugador) jugador.getUserData()).recogerArma(((CajaArma) objeto.getUserData()).getArma()); 

				} else ((Jugador) jugador.getUserData()).consumirItem(((CajaItem) objeto.getUserData()).getItem()); 

			}
		}
		verColisionBala(contact);

	}
	private void verColisionBala(Contact contact){
		if(Bala.class.isInstance(contact.getFixtureA().getUserData()) || Bala.class.isInstance(contact.getFixtureB().getUserData())) {
			Fixture objeto	=	Bala.class.isInstance(contact.getFixtureA().getUserData()) ? contact.getFixtureA() :  contact.getFixtureB() ;
			if(Persona.class.isAssignableFrom(contact.getFixtureA().getUserData().getClass()) || Persona.class.isAssignableFrom(contact.getFixtureB().getUserData().getClass())){

				 Fixture persona	=	Bala.class.isInstance(contact.getFixtureA().getUserData()) ? contact.getFixtureB() :  contact.getFixtureA() ;
				((Persona) persona.getUserData()).recibirDisparo(((Bala) objeto.getUserData()));
			
			}
			
			((Bala) objeto.getUserData()).colisiona(); 
			
	 }
		verColisionLimite(contact);
	}

	
	
	private void verColisionLimite(Contact contact){
		if((SoldadoEnemigo.class.isInstance(contact.getFixtureA().getUserData()) || SoldadoEnemigo.class.isInstance(contact.getFixtureB().getUserData())) 
				&& (Límite.class.isInstance(contact.getFixtureA().getUserData()) || Límite.class.isInstance(contact.getFixtureB().getUserData()))){
			
			Fixture enemigo	=	SoldadoEnemigo.class.isInstance(contact.getFixtureA().getUserData()) ? contact.getFixtureA() :  contact.getFixtureB() ;

			((SoldadoEnemigo)enemigo.getUserData()).invertirVelocidad(true, false); 
		}
		verColisionPuertaFinal(contact);
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	
	}

}
