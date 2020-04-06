package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends Thread {
	
	private String action;
	private String ip;
	private int port;
	private String key;
	private String value;
	
	// Start of put for TCP put!
	public UDPClient(String action, String ip, int port, String key, String value) throws IOException
	{
		this.action = action;
	    this.ip = ip;
	    this.port = port;
	    this.key = key;
	    this.value = value;
	}
	
	// Start of get and del for UDP
	public UDPClient (String action, String ip, int port, String key) throws IOException{	
		this.action = action;
        this.ip = ip;
        this.port = port;
        this.key = key;
	}
	
	// Start of store for UDP
	public UDPClient (String action, String ip, int port) throws IOException{
		this.action = action;
        this.ip = ip;
        this.port = port;
	}
	
	@ Override
	public void run() 
	{
		 // Step 1:Create the socket object for 
        // carrying the data. 
        DatagramSocket ds = null;
		try 
		{
			ds = new DatagramSocket();
		} 
		catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
        
        byte buf[] = null;
        
   	 	if(action.equals("store"))
   	 	{
	   	 	while (true) 
			{   	   
	
				String sentence;
					            
				sentence = action + " " + port + '\n';
				buf = sentence.getBytes();
					  
				// Step 2 : Create the datagramPacket for sending 
				// the data. 
				DatagramPacket DpSend = null;
				try {
					DpSend = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip),port);
				} 
				catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} 
					  
				// Step 3 : invoke the send call to actually send 
				// the data. 
				try {
					ds.send(DpSend);
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
					            
				////////////////////////////
				// Step 1 : Create a socket to listen at port 1235 
				DatagramSocket os = null;
				
				try {
					os = new DatagramSocket(port+1);
				} 
				catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				byte[] receive = new byte[60000]; 
					      
				DatagramPacket DpReceive = null; 
					            
					     
				// Step 2 : create a DatgramPacket to receive the data. 
				DpReceive = new DatagramPacket(receive, receive.length); 
						  
				// Step 3 : receive the data in byte buffer. 
				try {
					os.receive(DpReceive);
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				String value_from_server=data(receive).toString();
				
				String[] animals = value_from_server.split(" ");
				
				/////  Try here again!
				System.out.println("server response: ");
				////

			    int animalIndex = 0;
			    for (String animal : animals) {
			         System.out.println(animal);
			         animalIndex++;
			     }
			    receive = new byte[60000]; 
					  		
				break;            
			}
   	 	}
   	 	else if(action.equals("get") || action.equals("del")) 
   	 	{
	   	 	while (true) 
			 {   	    
				 String sentence;
				            
				 sentence = action + " " + port + " " + key + '\n';
				 buf = sentence.getBytes();
				  
				 // Step 2 : Create the datagramPacket for sending 
				 // the data. 
				 DatagramPacket DpSend = null;
				try {
					DpSend = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip),port);
				} 
				catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				  
				 // Step 3 : invoke the send call to actually send 
				 // the data. 
				 try {
					ds.send(DpSend);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				            
				 ////////////////////////////
				 // Step 1 : Create a socket to listen at port 1235 
				DatagramSocket os = null;
				try {
					os = new DatagramSocket(port+1);
				} 
				catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				 byte[] receive = new byte[65535]; 
				      
				 DatagramPacket DpReceive = null; 
				            
				     
				 // Step 2 : create a DatgramPacket to receive the data. 
				 DpReceive = new DatagramPacket(receive, receive.length); 
					  
				 // Step 3 : receive the data in byte buffer. 
				try {
					os.receive(DpReceive);
				} 
				 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
					            
				 // Uncomment for debugging purposes
				 ////////////////////////////
				 //System.out.println("Client:-" + data(receive)); 
				 //////////////////////
				 
				 String value_from_server=data(receive).toString();
					       
				 
				 String value_from_server1;
				 value_from_server1=value_from_server.split(" ")[0];
					     
				 String value_from_server2;
				 value_from_server2=value_from_server.split(" ")[1];

				 
				 
				 if(action.equals("get")) {
					System.out.println("server response:get key="+ value_from_server1 + " get val=" + value_from_server2);
				 }
				 else if(action.equals("del")) {
					System.out.println("server response:delete key="+ value_from_server1);
				 }

				///////////////////
				receive = new byte[65535]; 
				
					   
				break;
				////
				//ds.close();
				//os.close();
				/////	        
			     
		     }
   	 	}
   	 	else if(action.equals("put")) 
   	 	{
   	 		while (true) 
	   	    {             
   	 			String sentence;
	   	 	         
	   	 	    //////////////////
	   	 	    sentence = action + " " + port +" "+ key + " " + value;
	   	 	    /////////////////////////////
	   	 	    buf = sentence.getBytes();
	   	 	  
	   	 	    // Step 2 : Create the datagramPacket for sending the data.    	 	         
	   	 	    DatagramPacket DpSend = null;
				try {
					DpSend = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip),port);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	
	   	 	    // Step 3 : invoke the send call to actually send the data. 
	   	 	    try {
					ds.send(DpSend);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	   	 	    ////////////////////////////
	   	 	    
	   	 		// Step 1 : Create a socket to listen at port 1235 
	   	 		DatagramSocket os = null;
				try {
					os = new DatagramSocket(port+1);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	   	 		byte[] receive = new byte[60000]; 
	   	 		        
	   	 		DatagramPacket DpReceive = null; 
	   	 		
	   	 		// Step 2 : create a DatgramPacket to receive the data. 
	   	 		DpReceive = new DatagramPacket(receive, receive.length); 
	   	 			  
	   	 		// Step 3 : receive the data in byte buffer. 
	   	 		try {
					os.receive(DpReceive);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	
	   	 		String value_from_server=data(receive).toString();
	   	 			        
	   	 		value_from_server=value_from_server.split(" ")[2];
	   	 			        
	   	 		System.out.println("server response:put key="+ value_from_server);
	   	 			            
	   	 		receive = new byte[60000]; 
	   	 			              			  
	   	 		break;
	   	 	    
	   	 		     ////
	   	 		    // ds.close();
	   	 		     //os.close();
	   	 		     /////
	   	        
	   	    } 
   	 	}
   	}
	// A utility method to convert the byte array 
    // data into a string representation. 
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    } 
}
