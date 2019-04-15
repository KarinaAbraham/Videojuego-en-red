package com.videojuegored.game;

import java.net.InetAddress;
import java.util.concurrent.LinkedBlockingQueue;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.clienteservidor.Cliente;
import com.videojuegored.clienteservidor.Servidor;
import com.videojuegored.comunicadores.*;
import com.videojuegored.items.Arma;
import com.videojuegored.mecanicas.MyInput;
import com.videojuegored.mecanicas.MyInputListener;
import com.videojuegored.pantallas.*;
import com.videojuegored.personajes.Jugador;
import com.videojuegored.recursos.Recursos;
import com.videojuegored.red.WorldState;


public class MyGame extends Game {
	private SpriteBatch batch;
	private PantallaMenuPrincipal pantallaMenuPrincipal = new PantallaMenuPrincipal(this);
	private PantallaMultiplayer pantallaMultiplayer = new PantallaMultiplayer(this);
	private PantallaUnirseAPartida pantallaUnirseAPartida = new PantallaUnirseAPartida(this);
	private PantallaJuego pantallaJuego = new PantallaJuego(this);
	private World world;
	private int nivelActual; 
	private TextureAtlas  atlasPersona;
	private static final int NUM_MAXIMO_JUGADORES	=	2;
	private static final int I_JUG_1	=	0;
	private static final int I_JUG_2	=	1;
	private Jugador[] jugador;
	private LinkedBlockingQueue <MyInput>	entrada; 
	private Comunicador comunicador;
	private MyInputListener myInputListener; 
	private Hud hud;
	private LinkedBlockingQueue<WorldState> worldState; 
	private boolean segundoJugador;
	private boolean multiplayer;
	private Servidor servidor;
	private boolean conexionCaida;
	private Cliente cliente;
	private final int PORT = 8874;
	private boolean conectado;

	@Override
	public void create () {
		conectado = false;
		world =   new World(new Vector2(0, -10), true); ;
		entrada	= new LinkedBlockingQueue<MyInput>();
	    worldState= new LinkedBlockingQueue<WorldState>();
		batch = new SpriteBatch();
		comunicador = new ComunicadorLocal(entrada);
		multiplayer = false;
		segundoJugador = false;
		jugador =	new Jugador[NUM_MAXIMO_JUGADORES];
		myInputListener = new MyInputListener(this.comunicador);
		atlasPersona = new TextureAtlas(Gdx.files.internal(Recursos.ATLAS_PERSONA));
		jugador[I_JUG_1] = new Jugador(3000, new Arma("Arma Principal", Recursos.TEXTURA_BALA, 20), world, atlasPersona, "Jugador Uno"); 
		agregarAnimacionesJugadorUno();	
		nivelActual = 1;
		conexionCaida = false;
		hud	=	new Hud(this); 
		setScreen(pantallaMenuPrincipal); 
	}

	@Override
	public void render () { 
		try{
		super.render(); 
		
		}catch(Exception  e){
			if(cliente!= null){
				cliente.cerrar();
				e.printStackTrace();
			}
			
			if(servidor!=null){

				e.printStackTrace();
				servidor.cerrar();
			}
		}
	}
	@Override
	public void dispose () { 
		batch.dispose();
		world.dispose();
		atlasPersona.dispose();
		if(cliente!= null){
			cliente.cerrar();
		}
		
		if(servidor!=null){
			servidor.cerrar();
		}
	}
	
	
	public void pasarNivel(){ 
		if(nivelActual<3){

			nivelActual++;
			
		}
	}
	public void setConexionCaida(boolean conexionCaida){ 
		this.conexionCaida	=	conexionCaida;
	}
	
	public void setConectado(boolean conectado){ 
		this.conectado	=	conectado;
	}
	private void agregarAnimacionesJugadorUno(){ 
		jugador[I_JUG_1].setBody(100 ,100, 40, 40);					
		jugador[I_JUG_1].setAnimacionCorrer(0, 0, 94, 116, 5);	
		jugador[I_JUG_1].setAnimacionSaltar(0, 124, 100, 116, 5);
		jugador[I_JUG_1].setAnimacionQuieto(0, 260, 94, 116,1);

	}
	
	public void setMultiplayer(InetAddress ip){ 
	
		multiplayer = true;
		if(ip!=null){ 
			if(cliente==null)
				cliente = new Cliente(ip, PORT, this);
			
			comunicador = new ComunicadorCliente(cliente);
			 myInputListener  = new MyInputListener(this.comunicador); 
			 segundoJugador = true; 
			this.cliente.iniciar(); 

		}else{ 
			if(servidor==null)
				servidor = new Servidor(PORT, this);
				this.servidor.iniciar();
			
		}
		setJugadorDos(); 
		hud	=	new Hud(this); 

	}

	
	private void setJugadorDos(){ 
		jugador[I_JUG_2] = new Jugador(3000, new Arma("Arma Principal", Recursos.TEXTURA_BALA,  20), world, atlasPersona, "Jugador Dos");
		jugador[I_JUG_2].setBody(100, 100, 40, 40);
		jugador[I_JUG_2].setAnimacionCorrer(0, 0, 90, 118, 6);
		jugador[I_JUG_2].setAnimacionSaltar(0, 104, 90, 118, 4);
		jugador[I_JUG_2].setAnimacionQuieto(0, 250, 90, 100, 1);



	}


	public LinkedBlockingQueue<MyInput> getEntrada()	{return entrada;}	
	public MyInputListener getMyInputListener()	{return myInputListener;}	
	public PantallaMenuPrincipal getPantallaMenuPrincipal()	{return pantallaMenuPrincipal;}	
	public PantallaMultiplayer getPantallaMultiplayer() {return pantallaMultiplayer;}	
	public PantallaUnirseAPartida getPantallaUnirseAPartida() {return pantallaUnirseAPartida;}	
	public PantallaJuego getPantallaJuego() {return pantallaJuego;}	
	public SpriteBatch getSB() {return batch;}
	public Jugador getJugador (int iJugador){return this.jugador[iJugador];}
	public Jugador[] getJugador (){return this.jugador;}
	public LinkedBlockingQueue<WorldState> getWorldState(){return worldState;}
	public World getWorld() {return world;}
	public TextureAtlas getAtlasPersona()	{return atlasPersona;}	
	public Hud getHud()	{return hud;}	
	public int getNivelActual(){return nivelActual;}
	public boolean esSegundoJugador(){return segundoJugador;}
	public boolean esPartidaMultiplayer(){return multiplayer;}
	public Servidor getServidor(){return servidor;} 
	public Cliente getCliente(){return cliente;}
	public boolean seCayoLaConexion(){return conexionCaida;}
	public boolean seConecto(){return conectado;}

}
