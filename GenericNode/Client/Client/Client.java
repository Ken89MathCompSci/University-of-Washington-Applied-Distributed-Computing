package Client;


import java.io.*;    
import java.net.*;

public class Client {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		///////////////////////////////////////
		
		// store input parameters for RMI
        String protocolType = args[0];
        String serverIp = args[1];
        String portStr = args[2];
        int port = Integer.parseInt(portStr);    
        String operation = args[3];
        String key = " ";
        String value = " ";
        if(operation.equals("put")){
            key = key + args[4];
            value = value + args[5];
        }
        if(operation.equals("get") || operation.equals("del")){
            key = key + args[4];
        }
        //////////////
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
		else if (args[0].equals("uc")){
			if(args[3].equals("put")) {
				UDPClient obj1 =  new UDPClient(args[3],args[1], Integer.parseInt(args[2]), args[4], args[5]);
				obj1.start();
			}
			
			if(args[3].equals("get")) {
				UDPClient obj1 = new UDPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
				obj1.start();
			}
			if(args[3].equals("del")) {
				UDPClient obj1 = new UDPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
				obj1.start();
			}
			if(args[3].equals("store")) {
				UDPClient obj1 = new UDPClient(args[3], args[1], Integer.parseInt(args[2]));
				obj1.start();
			}
			if(args[3].equals("exit")) {
				System.out.println("<the server then exits>");
			}
		}
        
		else if (args[0].equals("rmic")) {
			
			RMIClient rmiclient = new RMIClient(serverIp, port, operation, key, value);

			
		/***
			if(args[2].equals("put")) {
				obj.RMIClient (args[2],args[1], args[3], args[4]);
			}
			
			if(args[3].equals("get")) {
				obj.UDPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
			}
			if(args[3].equals("del")) {
				obj.UDPClient(args[3], args[1], Integer.parseInt(args[2]), args[4]);
			}
			if(args[3].equals("store")) {
				obj.UDPClient(args[3], args[1], Integer.parseInt(args[2]));
			}
			if(args[3].equals("exit")) {
				System.out.println("<the server then exits>");
			}***/
			
		}
	}
}
