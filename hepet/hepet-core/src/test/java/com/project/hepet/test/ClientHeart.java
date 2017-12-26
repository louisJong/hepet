package com.project.hepet.test;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHeart implements Runnable {

	@Override
	public void run() {
			try {
				
				Socket sender = new Socket(InetAddress.getLocalHost(), 9090);
				while(true){
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(sender.getOutputStream());
					Entity en = new Entity();
					en.setName("心跳测试");
					en.setSex("男");
					objectOutputStream.writeObject(en);
					objectOutputStream.flush();
					System.out.println(" client heartbeat send one");
					Thread.sleep(2000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}

	public static void main(String[] args){
		new Thread(new ClientHeart()).start();
	}
}
