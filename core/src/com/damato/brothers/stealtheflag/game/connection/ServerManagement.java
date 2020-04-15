package com.damato.brothers.stealtheflag.game.connection;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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
	}
	
	public void connectServer(GameScreen gameScreen, Player player) {
		try {
			this.socket = IO.socket("http://192.168.2.8:3000");
			socket.connect();
			this.userPlayers = new HashMap<String, Player>();
			configSocketEvents(gameScreen, player);
		} catch (URISyntaxException e) {
			Gdx.app.log("SERVER", "ERRO AO SE CONECTAR: " + e);
		}
		
		
	}
	
	private void configSocketEvents(GameScreen gameScreen, Player player) {
		
		final GameScreen gScreen = gameScreen;
		final Player myPlayer = player;
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
				// envia as informacoes iniciais do jogador para o servidor
				JSONObject object = new JSONObject();
				try {
					object.put("x", myPlayer.b2body.getPosition().x);
					object.put("y", myPlayer.b2body.getPosition().y);
					object.put("state", myPlayer.getState().toString());
					object.put("direction", myPlayer.getDirectionR());
					object.put("life", myPlayer.getLife());
					
					JSONObject armObject = new JSONObject();
					armObject.put("name", myPlayer.getArm());
					armObject.put("countBullet", myPlayer.getCountBullet());
					armObject.put("countRechargeBullet", myPlayer.getCountRechargeBullet());
					object.put("arm", armObject);
					
					Gdx.app.log("connect", "" + myPlayer.getState().toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				socket.emit("myPlayer", object);
				
			}
		}).on("socketID", new Emitter.Listener() { // Recebe seu proprio ID
			
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
		}).on("newPlayer", new Emitter.Listener() { // Recebe o
			
			@Override
			public void call(Object... args) {
				JSONObject object = (JSONObject) args[0];
				try {
					String id = object.getString("id");
					Gdx.app.log("SocketIO", "New Player: " + id);
					
					userPlayers.put(id, new Player(gScreen));
					Gdx.app.log("NEWPLAYER", "id: " + id);
					
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
					
					if ( gScreen != null ) {
						for ( int i = 0; i < objects.length(); i++ ) {
							Player player = new Player(gScreen);
							
							float x = ( (Double) objects.getJSONObject(i).getDouble("x") ).floatValue();
							float y = ( (Double) objects.getJSONObject(i).getDouble("y") ).floatValue();
							
							player.setPosition(new Vector2(x, y));
							player.currentState = Player.State.valueOf(objects.getJSONObject(i).getString("state"));
							boolean direction = objects.getJSONObject(i).getBoolean("direction");
							player.setDirectionR(direction);
							player.setLife(objects.getJSONObject(i).getInt("life"));
							player.setArm(Player.Arm.valueOf(
									( (JSONObject) objects.getJSONObject(i).getJSONObject("arm")).getString("name") 
									));
							player.setCountBullet(
									( (JSONObject) objects.getJSONObject(i).getJSONObject("arm")).getInt("countBullet") 
									);
							player.setCountRechargeBullet(
									( (JSONObject) objects.getJSONObject(i).getJSONObject("arm")).getInt("countRechargeBullet")
									);
							
							userPlayers.put(objects.getJSONObject(i).getString("id"), player);
							Gdx.app.log("GETPLAYERS", "id: " + objects.getJSONObject(i).getString("id") );
						}
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
		}).on("updatePlayer", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				
				try {
					String playerId = data.getString("id");
					
					float x = ( (Double) data.getDouble("x") ).floatValue();
					float y = ( (Double) data.getDouble("y") ).floatValue();
					String state = data.getString("state");
					boolean direction = data.getBoolean("direction");
					int life = data.getInt("life");
					if ( userPlayers.get(playerId) != null ) {
						userPlayers.get(playerId).setPosition(new Vector2(x, y));
						userPlayers.get(playerId).currentState = Player.State.valueOf(state);
						userPlayers.get(playerId).setDirectionR(direction);
						userPlayers.get(playerId).setLife(life);
						userPlayers.get(playerId).setArm(Player.Arm.valueOf(
								( (JSONObject) data.getJSONObject("arm")).getString("name") 
								));
						userPlayers.get(playerId).setCountBullet(
								( (JSONObject) data.getJSONObject("arm")).getInt("countBullet") 
								);
						userPlayers.get(playerId).setCountRechargeBullet(
								( (JSONObject) data.getJSONObject("arm")).getInt("countRechargeBullet")
								);
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
					userPlayers.get(id).b2body = null;
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
