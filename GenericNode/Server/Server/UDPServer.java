package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer extends Thread {
	private int port;
	
	public UDPServer(int port) throws IOException{
		this.port = port;
	}

	@ Override
	public void run() {
		try {
			// Step 1 : Create a socket to listen at port 1234 
	        DatagramSocket ds = new DatagramSocket(port); 
	        byte[] receive = new byte[60000]; 
	  
	        DatagramPacket DpReceive = null; 
	        while (true) 
	        { 
	        	DpReceive = new DatagramPacket(receive, receive.length); 
                
	        	ds.receive(DpReceive);
	 
	        	////
	        	receive = new byte[60000]; 
	        	////
	        	
	        	new Thread(new UDPServerThread(ds, DpReceive)).start();
	        }
		}
	    catch(IOException i){
			System.out.println(i.getMessage());
		}         
	}
}
