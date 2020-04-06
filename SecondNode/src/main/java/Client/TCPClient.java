package Client;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends Thread{
	
	private String action;
	private String ip;
	private int port;
	private String key;
	private String value;
	
	// Start of put for TCP put!
	public TCPClient(String action, String ip, int port, String key, String value) throws IOException
    {
        this.action = action;
        this.ip = ip;
        this.port = port;
        this.key = key;
        this.value = value;
    }
	
	// Start of get and del for TCP!
	public TCPClient(String action, String ip, int port, String key) throws IOException
	{
		this.action = action;
		this.ip = ip;
		this.port = port;
		this.key = key;
	}
	
	// Start of store for TCP!
	public TCPClient (String action, String ip, int port) throws IOException{
		this.action = action;
		this.ip = ip;
		this.port = port;
	}
	
	@ Override
	 public void run() 
	 {
		 String sentence;
		 String modifiedSentence;

		 Socket clientSocket;
		
		 try {
			 if(action.equals("put")) {
				 clientSocket = new Socket(ip, port);
				 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				  
				 sentence = action + " " + key + " " + value;
				  
				 /////
				 outToServer.writeBytes(sentence + '\n');
				 ////
				 
				 modifiedSentence = inFromServer.readLine();
				 System.out.println("server response:put key=" + modifiedSentence);
				 clientSocket.close();
			 }
			 
			 else if(action.equals("get") || action.equals("del")) {
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

				clientSocket = new Socket(ip, port);
						  			
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
				sentence = action+ " " +key;
				outToServer.writeBytes(sentence + '\n');
				modifiedSentence = inFromServer.readLine();
				
				if(action.equals("get")) {
					System.out.println("server response:get key=" +key + " get val=" +modifiedSentence);
				}
				else if(action.equals("del")) {
					System.out.println("server response:del key=" +key);
				}
				clientSocket.close();
			 }
			 else if(action.equals("store")) {
				 
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

				clientSocket = new Socket(ip, port);
						  
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					  
				sentence = action;
				outToServer.writeBytes(sentence + '\n');
				modifiedSentence = inFromServer.readLine();
	
				String[] output_list_of_strings = modifiedSentence.split(" ");
					
				/////  Try here again!
				System.out.println("server response: ");
				////
						
				int stringIndex = 0;
				for (String output_string : output_list_of_strings) {
					System.out.println(output_string);
				    stringIndex++;
				}
				      
				clientSocket.close(); 
			 }
		}
				
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	 }
}
