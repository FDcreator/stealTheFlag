package com.damato.brothers.stealtheflag.game.connection;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.damato.brothers.stealtheflag.game.screens.GameScreen;
import com.damato.brothers.stealtheflag.game.sprites.Player;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.emitter.Emitter.Listener;

public class ServerManagement {
	
	private Socket socket;

	private HashMap<String, Player> userPlayers;
	
	public ServerManagement() {
		connectServer();
		
		this.userPlayers = new HashMap<String, Player>();
	}
	
	public void connectServer() {
		try {
			this.socket = IO.socket("http://192.168.2.8:3000");
			socket.connect();
		} catch (URISyntaxException e) {
			Gdx.app.log("SERVER", "ERRO AO SE CONECTAR: " + e);
		}
	}
	
	public void configSocketEvents(GameScreen gameScreen) {
		
		final GameScreen gScreen = gameScreen;
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
				
			}
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				socket.emit("disconnect", socket.id());
				
			}
		}).on("socketID", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject object = (JSONObject) args[0];
				try {
					String id = object.getString("id");
					
					Gdx.app.log("SocketIO", "PlayerID: " + id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).on("newPlayer", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject object = (JSONObject) args[0];
				try {
					String id = object.getString("id");
					Gdx.app.log("SocketIO", "New Player: " + id);
					
					userPlayers.put(id, new Player(gScreen));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).on("playerDisconnect", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject object = (JSONObject) args[0];
				
				try {
					String id = object.getString("id");
					gScreen.getWorld().destroyBody(userPlayers.get(id).b2body);
					userPlayers.remove(id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
}
