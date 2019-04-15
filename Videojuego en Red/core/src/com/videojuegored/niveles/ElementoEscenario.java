package com.videojuegored.niveles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public abstract class ElementoEscenario {
 
		protected World world;
		protected TiledMap tiledMap;
		protected Rectangle objeto;
		protected Body body;
		protected Fixture fixture;
		
		public ElementoEscenario(World world, TiledMap tiledMap, Rectangle objeto){ 
			this.world		=	world;
			this.tiledMap	=	tiledMap;
			this.objeto		=	objeto;
			
			BodyDef bodyDef = new BodyDef();
			FixtureDef fixtureDef = new FixtureDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;			
			bodyDef.position.set(((objeto.getX() + objeto.getWidth() / 2) / OtrasConstantes.PPM) , ((objeto.getY() + objeto.getHeight() / 2) / OtrasConstantes.PPM) );
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(objeto.getWidth() / 2 / OtrasConstantes.PPM,  objeto.getHeight() / 2 / OtrasConstantes.PPM);
			fixtureDef.shape = shape;
			

			fixtureDef.filter.categoryBits = OtrasConstantes.BIT_DEFECTO;  
			fixtureDef.filter.maskBits = OtrasConstantes.BIT_JUGADOR;
			body = world.createBody(bodyDef);
			fixture	= body.createFixture(fixtureDef);
			
		}
		
		public void eliminarBody(){ 
			if(body!=null) world.destroyBody(body);

		}
	
		public void setCategoryFilter(short filterBit){ 
			Filter filter	=	new	Filter();
			filter.categoryBits	=	filterBit;
			fixture.setFilterData(filter);
			
		}
	
		public void setFilter(short categoryBit, short maskBit){ 
			Filter filter	=	new	Filter();
			
			filter.maskBits	=	maskBit;
			filter.categoryBits = categoryBit;
			fixture.setFilterData(filter);
			
		}
		

}
