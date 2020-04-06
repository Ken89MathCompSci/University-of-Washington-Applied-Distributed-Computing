package genericnode.GenericNode;

import java.io.*;
import java.net.*;

public class App 
{
	 public static void main(String argv[]) throws Exception
	  {
		 	App obj = new App();
			
		 	if (argv.length == 0){
		 		System.out.println("us/ts <port> UDP/TCP/TCP-and-UDP SERVER: run server on <port>.\r\n" + 
						"tus <tcpport> <udpport> TCP-and-UDP SERVER: run servers on <tcpport> and\r\n" + 
						"<udpport> sharing same key-value store.\r\n" + 
						"alls <tcpport> <udpport> TCP, UDP, and RMI SERVER: run servers on <tcpport>\r\n" + 
						"and <udpport> sharing same key-value store.\r\n" + 
						"rmis RMI Server.  ");  
		 	}
		 	/***
		 	if (args[0].equals("ts")){
				obj.TCPServer(Integer.parseInt(args[1]));
			}***/
		 	ServerSocket welcomeSocket = new ServerSocket(6789);
	 
		 	while(true)
		 	{
		 		Socket connectionSocket = welcomeSocket.accept();
		 		if (connectionSocket != null)
		 		{
		 			Client client = new Client(connectionSocket);
		 			client.start();
		 		}
		 	}
	  }	
}
