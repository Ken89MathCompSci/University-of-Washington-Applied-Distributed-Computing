package Client;


import java.io.*;    
import java.net.*;

public class Client {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Client obj = new Client();
        
        if (args.length == 0){
			System.out.println("us/ts <port> UDP/TCP/TCP-and-UDP SERVER: run server on <port>.\r\n" + 
					"tus <tcpport> <udpport> TCP-and-UDP SERVER: run servers on <tcpport> and\r\n" + 
					"<udpport> sharing same key-value store.\r\n" + 
					"alls <tcpport> <udpport> TCP, UDP, and RMI SERVER: run servers on <tcpport>\r\n" + 
					"and <udpport> sharing same key-value store.\r\n" + 
					"rmis RMI Server.  ");  
		}
		else if (args[0].equals("tc")){
			
			if(args[3].equals("exit")) {
				System.out.println("<the server then exits>");
			}
			
			if(args[3].equals("store")) {
				TCPClient obj1 = new TCPClient(args[3], args[1], Integer.parseInt(args[2]));
				obj1.start();
			}
			if(args[3].equals("del")) {
				TCPClient obj1 = new TCPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
				obj1.start();
			} 
			if(args[3].equals("get")) {
				TCPClient obj1 = new TCPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
				obj1.start();
			}
			if(args[3].equals("put")) {
				TCPClient obj1 = new TCPClient(args[3],args[1], Integer.parseInt(args[2]), args[4], args[5]);
				obj1.start();
			}
		}
		
	}
}
