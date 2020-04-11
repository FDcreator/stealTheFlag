package com.damato.brothers.stealtheflag.game.connection;

import java.net.URISyntaxException;

import com.badlogic.gdx.Gdx;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ServerManagement {
	
	private Socket socket;

	public ServerManagement() {
		connectServer();
	}
	
	public void connectServer() {
		try {
			this.socket = IO.socket("http://192.168.2.8:3000");
			socket.connect();
		} catch (URISyntaxException e) {
			Gdx.app.log("SERVER", "ERRO AO SE CONECTAR: " + e);
		}
	}
}
