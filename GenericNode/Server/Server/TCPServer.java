package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{
	private int ip;
	
	public TCPServer(int ip) throws IOException{
		this.ip = ip;
	}
	
	@ Override
	public void run() {
		try {
			ServerSocket welcomeSocket = new ServerSocket(ip);
			
	
			while (true) {
			   Socket connectionSocket = welcomeSocket.accept();
			   
			   ////// Start a new thread for every single connecting client to the TCP server!
			   new Thread(new TCPServerThread(connectionSocket)).start();
			   ////
		    }
		}
	
		catch(IOException i){
			System.out.println(i);
		}
	}
}
