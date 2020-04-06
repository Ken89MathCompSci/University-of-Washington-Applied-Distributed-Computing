package Server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Map;
import java.util.stream.Stream;
import java.net.InetAddress;

public class Server {
	

	int ip;

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
		// IMportant!!! Enable this to allow automatic removal of own IPAddresses:PORT
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run() {
	            try {
	                Thread.sleep(200);
	                //System.out.println("Shutting down ...");
	                
	                // Get own IP Address

	                InetAddress inetAddress = InetAddress.getLocalHost();
	                String lineToRemove = String.valueOf(inetAddress.getHostAddress()) + ":" + String.valueOf(args[1]);
	                
	                
	                /***
	                ////////////////////////////////////
	                // Create a file object 
	                File f = new File("nodes.cfg"); 
	      
	                // get the absolute path 
	                // of file f 
	                //String absolute = f.getAbsolutePath(); 
	                System.out.println("absolute ");
	                
	                ////////////////////////////////////***/
	                
	                // To change directory when changing from testing in Windows to linux
	                //BufferedReader file = new BufferedReader(new FileReader("C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg"));
	               // BufferedReader file = new BufferedReader(new FileReader(absolute));
	                BufferedReader file = new BufferedReader(new FileReader("/tmp/nodes.cfg"));
	                
	               
	                
	                String line;
	                String input = "";
	                while ((line = file.readLine()) != null) 
	                {
	                    //System.out.println(line);
	                    if (line.contains(lineToRemove))
	                    {
	                        line = "";
	                        //System.out.println("Line deleted.");
	                    }
	                    input += line + '\n';
	                }
	                
	             // To change directory when changing from testing in Windows to linux
	                //FileOutputStream File = new FileOutputStream("C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg");
	                //FileOutputStream File = new FileOutputStream(absolute);
	                FileOutputStream File = new FileOutputStream("/tmp/nodes.cfg");
	                
	                File.write(input.getBytes());
	                file.close();
	                File.close();

	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                e.printStackTrace();
	            } catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	}

}
