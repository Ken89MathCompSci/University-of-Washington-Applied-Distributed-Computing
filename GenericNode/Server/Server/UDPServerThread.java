package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Create a new UDP server thread for each incoming UDP Client
class UDPServerThread implements Runnable {
	private DatagramPacket packet;
	private DatagramSocket ds;
	
	////////////////////////////////////////////////////
	/* This is how to declare HashMap */
	public static HashMap<String, String> hmap = new HashMap<String, String>();
	//////////////////////////////////////////////////////

	public UDPServerThread(DatagramSocket ds, DatagramPacket packet) {
		this.packet = packet;
		this.ds = ds;
	}

	@ Override
	public void run() {
		try {
	            
	            String clientSentence;
	            String parameter;
	            
	            clientSentence = new String(this.packet.getData());

	            parameter = clientSentence.split(" ")[0];
	    	            
	            if(parameter.equals("put"))
	 		   	{
	 			   	String parameter1;
	 			   	String parameter2;
	 			   
	 			   	parameter1 = new String(clientSentence.split(" ")[2]);	 	 			   
	 			   	parameter2 = new String(clientSentence.split(" ")[3]);

	 		   		hmap.put(parameter1, parameter2);
	 		   
	 		   		// Step 1:Create the socket object for 
	 		        // carrying the data. 
	 		        DatagramSocket os = new DatagramSocket(); 
	 		  
	 		        byte buf[] = null;
	 		        
	 		        
	 		        // convert the String input into the byte array. 
	 	            buf = clientSentence.getBytes(); 
	 	  
	 	            // Step 2 : Create the datagramPacket for sending the data. 

	 	            int port_value=Integer.valueOf(clientSentence.split(" ")[1])+1;
	 	            
	 	            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, this.packet.getAddress(), port_value); 	            
	 	         
	 	            // Step 3 : invoke the send call to actually send the data. 
	 	            os.send(DpSend); 
	 	                     
	 		   }           
	            else if(parameter.equals("get")) {

	            	String parameter1;
	   			   	String capitalizedSentence;
	
	   			   	parameter1 = clientSentence.split(" ")[2].trim();
	   			   	

	 			   
	   			   	capitalizedSentence = parameter1.trim() + " " + String.valueOf(hmap.get(parameter1));
	   		   
	   		   		// Step 1:Create the socket object for 
	   		        // carrying the data. 
	   		        DatagramSocket os = new DatagramSocket(); 
	   		  
	   		        InetAddress ip = InetAddress.getLocalHost(); 
	   		        byte buf[] = null;
	   		        
	   		        
	   		        // convert the String input into the byte array. 
	   	            buf = capitalizedSentence.getBytes(); 
	   	  
	   	            // Step 2 : Create the datagramPacket for sending the data. 
	   	            int port_value = Integer.valueOf(clientSentence.split(" ")[1])+1;
	   	            
	   	            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, packet.getAddress(), port_value); 	  
	   	  
	   	            // Step 3 : invoke the send call to actually send the data. 
	   	            os.send(DpSend); 
	            	
	            }
	            
	           else if(parameter.equals("del")) {
	            	
	            	String parameter1;
	   			   	String capitalizedSentence;
	
	   			   	parameter1 = clientSentence.split(" ")[2].trim();
	 			  
	   			   	capitalizedSentence = parameter1 + " " + String.valueOf(hmap.remove(parameter1));
	   			   	
	   			   	
	   		   		// Step 1:Create the socket object for 
	   		        // carrying the data. 
	   		        DatagramSocket os = new DatagramSocket(); 
	   		  
	   		        InetAddress ip = InetAddress.getLocalHost(); 
	   		        byte buf[] = null;
	   		        
	   		        
	   		        // convert the String input into the byte array. 
	   	            buf = capitalizedSentence.getBytes(); 
	   	  
	   	            // Step 2 : Create the datagramPacket for sending the data. 
	   	            
	   	            int port_value = Integer.valueOf(clientSentence.split(" ")[1])+1;
	   	            
	   	            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, packet.getAddress(), port_value); 	  
	   	            // Step 3 : invoke the send call to actually send the data. 
	   	        
	   	            os.send(DpSend);       	
	            }
	            
	            else
	            {
	            	//////////////
	            	int port_value = Integer.valueOf(clientSentence.split(" ")[1].trim()) + 1;
	            	///////////
	            	
	   			   	///////////////////////////////////////////////////////////
	   			   	String outputMsg ="";
	   			   	
			   		Iterator hmIterator = hmap.entrySet().iterator();
			   		
			   		if(!hmIterator.hasNext()) {
			   			outputMsg="Null";
			   		}
			   		
			   		while(hmIterator.hasNext()) {
			   			Map.Entry element = (Map.Entry) hmIterator.next();
			   					   			
			   			String key =  element.getKey().toString().trim();
			   			String value = element.getValue().toString().trim();
			
			   			String para = value.split(" ")[0];			   			
			   			
			   			outputMsg = outputMsg+ "key:" +key+ ":value:"+ value +": ";
			   				   			
			   			///////////////////// Changed
			   			if(outputMsg.getBytes().length > 65000) {
			   				byte[] trimmedMsg = Arrays.copyOfRange(outputMsg.getBytes(), 0, 64999);
			   				outputMsg = "";
			   				outputMsg = "TRIMMED: " + new String(trimmedMsg);
			   			}
			   		}

			   		outputMsg=outputMsg.trim();
			   		
	   			   	///////////////////////////////////////
	   			   	
	   		   		// Step 1:Create the socket object for 
	   		        // carrying the data. 
	   		        DatagramSocket os = new DatagramSocket(); 
	   		  
	   		        byte buf[] = null;	   		        
	   		        
	   		        // convert the String input into the byte array. 
	   	            buf = outputMsg.getBytes(); 
	   	  
	   	            // Step 2 : Create the datagramPacket for sending the data. 

	   	            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, packet.getAddress(), port_value); 	  
	   	  
	   	            // Step 3 : invoke the send call to actually send the data. 
	   	            os.send(DpSend);           	
	            }
		} 
		catch (IOException e) {
			e.printStackTrace();
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