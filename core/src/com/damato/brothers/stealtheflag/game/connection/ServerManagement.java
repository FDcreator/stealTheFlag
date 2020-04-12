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
		
		this.userPlayers = new HashMap<String, Player>();
	}
	
	public void connectServer(GameScreen gameScreen) {
		try {
			this.socket = IO.socket("http://192.168.2.8:3000");
			socket.connect();
			configSocketEvents(gameScreen);
		} catch (URISyntaxException e) {
			Gdx.app.log("SERVER", "ERRO AO SE CONECTAR: " + e);
		}
		
		
	}
	
	private void configSocketEvents(GameScreen gameScreen) {
		
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
		}).on("getPlayers", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				
				try {
					
					for ( int i = 0; i < objects.length(); i++ ) {
						Player player = new Player(gScreen);
						
						float x = ( (Double) objects.getJSONObject(i).getDouble("x") ).floatValue();
						float y = ( (Double) objects.getJSONObject(i).getDouble("x") ).floatValue();
						
						player.b2body.setTransform(x, y, 0);
						player.currentState = Player.State.valueOf(objects.getJSONObject(i).getString("state"));
						
						userPlayers.put(objects.getJSONObject(i).getString("id"), player);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
		}).on("playerMoved", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				
				try {
					String playerId = data.getString("id");
					
					float x = ( (Double) data.getDouble("x") ).floatValue();
					float y = ( (Double) data.getDouble("y") ).floatValue();
					String state = data.getString("state");
					if ( userPlayers.get(playerId) != null ) {
						userPlayers.get(playerId).b2body.setTransform(x, y, 0);
						userPlayers.get(playerId).currentState = Player.State.valueOf(state);
					}
					
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
	
	public HashMap<String, Player> getUserPlayers() {
		return userPlayers;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
