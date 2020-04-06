package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Map;


public class Server {
	
	private static Hashtable<String, String> hm;
	

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Server obj = new Server();

		if (args.length == 0){
			System.out.println("us/ts <port> UDP/TCP/TCP-and-UDP SERVER: run server on <port>.\r\n" + 
					"tus <tcpport> <udpport> TCP-and-UDP SERVER: run servers on <tcpport> and\r\n" + 
					"<udpport> sharing same key-value store.\r\n" + 
					"alls <tcpport> <udpport> TCP, UDP, and RMI SERVER: run servers on <tcpport>\r\n" + 
					"and <udpport> sharing same key-value store.\r\n" + 
					"rmis RMI Server.  ");  
		}
		if (args[0].equals("ts")){
			TCPServer obj1 = new TCPServer(Integer.parseInt(args[1]));
			obj1.start();
		}
		if (args[0].equals("us")){
			UDPServer obj1 = new UDPServer(Integer.parseInt(args[1]));
			obj1.start();
		}
		if(args[0].equals("rmis")) {		
			
			RMIServer rmiserver = new RMIServer(hm);
    		RMISkeleton skel = new RMISkeleton(rmiserver, Integer.parseInt(args[1]));
    		skel.start();
			
			/***
			try {
	            KeyValueServer kvserver = new KeyValueServer();
	            KeyValueStore stub = (KeyValueStore) UnicastRemoteObject.exportObject(kvserver, 0);

	            // Bind the remote object's stub in the registry
	            Registry registry = LocateRegistry.getRegistry();
	            registry.bind("KeyValueStore", stub);

	            System.out.println("KeyValueServer started");
	        } catch (Exception e) {
	            System.out.println("Error starting server " + e.toString());
	            e.printStackTrace();
	        }***/
		}
	}
}
