package com.project.hepet.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerHeat implements Runnable {

	private static ServerSocket server = null;
	Object obj = new Object();
	
	private ServerSocket getServer() throws IOException{
		if(server == null){
			synchronized (ServerHeat.class) {
				return new ServerSocket(9090);
			}
		}
		return server;
	}
	
	@Override
	public void run() {
		try {
			server = getServer();
			while(true){
				Socket client = server.accept();
				synchronized (obj) {
					Thread t = new Thread(new Client(client));
					t.start();
				}
				System.out.println(" finish one times heartbeat ");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		System.out.println(" server heartbeat is start ");
		new Thread(new ServerHeat()).start();
	}
}

class Client implements Runnable{
	Socket client;
	
	public Client(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			while(true){
					ObjectInputStream objInputStream = new ObjectInputStream(client.getInputStream());
					Entity entity = (Entity) objInputStream.readObject();
					System.out.println(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
}
