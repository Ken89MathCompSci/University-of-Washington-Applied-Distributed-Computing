package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TCPServerThread implements Runnable{
	Socket socket;
	
	////////////////////////////////////////////////////
    /* This is how to declare HashMap */
    public static HashMap<String, String> hmap = new HashMap<String, String>();
    //////////////////////////////////////////////////////

	public TCPServerThread(Socket socket) {
		this.socket = socket;
	}

	@ Override
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			OutputStream outToClient = socket.getOutputStream();

			
			String clientSentence = inFromClient.readLine();
			
			String parameter = clientSentence.split(" ")[0];
		
			if(parameter.equals("put"))
			{
				String parameter1;
				String parameter2;
			
				parameter1 = clientSentence.split(" ")[1];
				parameter2 = clientSentence.split(" ")[2];
				
				hmap.put(parameter1, parameter2);
				
				String capitalizedSentence = parameter1 + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());
			}
		
			else if(parameter.equals("get"))
			{
				String parameter1;
			
				parameter1 = clientSentence.split(" ")[1];
				
				String capitalizedSentence = String.valueOf(hmap.get(parameter1))  + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());
			
			}
		
			else if(parameter.equals("del"))
			{
				String parameter1;

				parameter1 = clientSentence.split(" ")[1];
				
				String capitalizedSentence = String.valueOf(hmap.remove(parameter1))  + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());
			
			}
		
			else if(parameter.equals("store"))
			{
				String outputMsg ="";
				Iterator hmIterator = hmap.entrySet().iterator();
				if(!hmIterator.hasNext()) {
					outputMsg="Null";
				}
		
				while(hmIterator.hasNext()) {
					Map.Entry element = (Map.Entry) hmIterator.next();
					
					//////
					String key = (String) element.getKey();
					String value = (String) element.getValue();
					////////////
					
					outputMsg = outputMsg + "key:" +key+ ":value:" + value + ":" + " ";
					
					if(outputMsg.getBytes().length > 65000) {
						byte[] trimmedMsg = Arrays.copyOfRange(outputMsg.getBytes(), 0, 64999);
						outputMsg = "TRIMMED: " + new String(trimmedMsg);
					}
				}
		
				outputMsg=outputMsg+'\n';
				   		
				outToClient.write(outputMsg.getBytes());
			}
			else if(parameter.equals("exit")) {
		
				String capitalizedSentence = "exit"  + '\n';

				outToClient.write(capitalizedSentence.getBytes());
			
				return;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
