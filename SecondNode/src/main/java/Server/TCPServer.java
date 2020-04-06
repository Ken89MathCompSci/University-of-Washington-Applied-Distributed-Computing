package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TCPServer extends Thread{
	public int ip;
	
	public TCPServer(int ip) throws IOException{
		this.ip = ip;
	}
	
	@ Override
	public void run() {
		try {
			ServerSocket welcomeSocket = new ServerSocket(ip);
			
			//////////////////////////////////////////////
		
			// get own IP address and port
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			// For debugging purposes
			//System.out.println("IP Address:- " + inetAddress.getHostAddress());
			//System.out.println("Host Name:- " + inetAddress.getHostName());
			//System.out.println("Port:- " + ip);
			/////////////////////////////////////////////////
			
			// IMportant!!! Enable this to allow automatic addition of IPAddresses:PORT
			////////////////////////////////////////////////////////////////////
			
			// Write own IP address and port to 'nodes.cfg.txt' file in "IPAddress:PORT" format
			//FileWriter fileWriter = new FileWriter("C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg", true); //Set true for append mode
		  /***
			 ////////////////////////////////////
            // Create a file object 
            File f = new File("nodes.cfg"); 
  
            // get the absolute path 
            // of file f 
            String absolute = f.getAbsolutePath(); 
           // System.out.println("");
            
            ////////////////////////////////////***/
			
			// To change directory when changing from testing in Windows to linux
			//FileWriter fileWriter = new FileWriter("nodes.cfg", true);
            
            //FileWriter fileWriter = new FileWriter(absolute, true);
			
			FileWriter fileWriter = new FileWriter("/tmp/nodes.cfg", true);
			
			PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.println(inetAddress.getHostAddress() + ":" + String.valueOf(ip));  //New line
		    printWriter.close();
		    ///////////////////////////////////////////////////////////////////////
		    
			while (true) {
			   Socket connectionSocket = welcomeSocket.accept();
			   
			   ////// Start a new thread for every single connecting client to the TCP server!
			   new Thread(new TCPServerThread(connectionSocket)).start();
			   ////
			   
			   /***
			   String fileName = "C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg.txt";
			   String lineToRemove = inetAddress.getHostAddress() + ":" + ip;	
			   try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
					stream.filter(line->!line.trim().equals(lineToRemove)).forEach(System.out::println);
			   } 
			   catch (IOException e) {
					e.printStackTrace();
			   }***/
		    }
			
		}
	
		catch(IOException i){
			System.out.println(i);
		}
		
	}
}
