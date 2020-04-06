package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentMap;


/////////////////////////////////////////
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/////////////////////////////////////////
import java.util.stream.Stream;
///////////////////////////////////////////
// To get own IP address and port
import java.net.InetAddress;
import java.net.ServerSocket;

////////////////////////////////////////////

//////////////////////////////////////////
// To read in java cfg file with IP addresses and ports
import java.io.*;
///////////////////////////////////////////



public class TCPServerThread implements Runnable{
	Socket socket;
	
	////////////////////////////////////////////////////
    /* This is how to declare HashMap */
    public static HashMap<String, String> hmap = new HashMap<String, String>();
    //////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////
    /* This is the list of "locked" keys! */
    
	public static List<String> list = new ArrayList<>();
	
    /////////////////////////////////////////////////////

	public TCPServerThread(Socket socket) {
		this.socket = socket;
	}


	@ Override
	public void run() {
		try {
			
			/////////////////////////////////////////////////////
		        
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			OutputStream outToClient = socket.getOutputStream();

			
			String clientSentence = inFromClient.readLine();
			
			String parameter = clientSentence.split(" ")[0];
			
			///////////////////////////////////////////////
			
			Socket clientSocket;
			
			////////////////////////////
	
			// if "put" is received by leader server node,
			if(parameter.equals("put"))
			{	
				
				String parameter1;
				String parameter2;
			
				parameter1 = clientSentence.split(" ")[1];
				parameter2 = clientSentence.split(" ")[2];
				
				////////////////////////////////////////////////////////////////
				// Have to a list to keep track of "locked" keys
			
				// then put "key" in "ArrayList" for locking
				list.add(parameter1);
	
				////////////////////////////////////////////////////////////////
			
				////////////////////////////////////////////////////////////////
				// read in existing IP addresses and ports of servers
				//	File file = new File("C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg"); 
				
				File file = new File("/tmp/nodes.cfg"); 
				/////////////////////////////////////////
				//String absolute = file.getAbsolutePath(); 
				///////////////////////////////////////

				
				//BufferedReader br = new BufferedReader(new FileReader(file)); 
				BufferedReader br = new BufferedReader(new FileReader(file)); 
				
				String st; 

				ArrayList<String> ar = new ArrayList<String>();
				
				// For debugging purposes!
				//System.out.println(ar.size());
				
				// Iterate through the static to load in IP addresses and ports into own "ArrayList", ar
				while ((st = br.readLine()) != null) {

					if(!st.equals("")) {
						ar.add(String.valueOf(st).trim());
					}
					
				} 
				
				br.close();
				
				/***
				for (String num : ar) { 		      
			           System.out.println(num); 		
			    }***/
				  

				//System.out.println(ar.size());
				 
				//////////////////////////////////////////////////////////////////////////////////////
				String part_one;
				String part_two;
				
				////////////////////////////////
				// Commit or don't commit!

				String reply_zero= "COMMIT!";
				////////////////////////////////
				
				String sentence="";
			
			
				// Iterate through the "ArrayList", ar and send "dput1" to all other server nodes and await reply from them.
				for (String num : ar) { 		
					
					part_one = num.split(":")[0];
					part_two = num.split(":")[1];
					
					/***
					System.out.println(part_one);
					System.out.println(part_two);***/
					
					// get all other IP addresses and ports except own, and send "dput1" command
					
					if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
					
					
						clientSocket = new Socket(part_one, Integer.parseInt(part_two));
						DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
						
						sentence = "dput1".trim() + " " + parameter1.trim() + " " + parameter2.trim();
						outToMemberServer.writeBytes(sentence + '\n');
						

						BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						String modifiedSentence = inFromServer.readLine();
						
						reply_zero = modifiedSentence.trim();
						
						//System.out.println(reply_zero);
					}
			    }
				
				//System.out.println("Result: " + reply_zero);
				
				// if all other server nodes agree to "COMMIT"
				if(reply_zero.equals("COMMIT!")){
					
					//////////////////////////////////////////
					
					list.remove(parameter1);
					
					//System.out.println("COMMIT IN PROGRESS!!!!!");
					
					hmap.put(parameter1, parameter2);
						
					String capitalizedSentence = parameter1 + '\n';
						
					outToClient.write(capitalizedSentence.getBytes());
						//////////////////////							

					for (String num : ar) { 		
						
						part_one = num.split(":")[0];
						part_two = num.split(":")[1];
						
						//System.out.println(part_one);
						//System.out.println(part_two);
						
						// get all other IP addresses and ports except own, and send "dput2" command
						if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
						
						
							clientSocket = new Socket(part_one, Integer.parseInt(part_two));
							DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
							
							sentence = "dput2".trim() + " " + parameter1.trim() + " " + parameter2.trim();
							outToMemberServer.writeBytes(sentence + '\n');
							

							
							//System.out.println("Done!!!!!!");
						}
				    }
				}
				
				// else if at least one server nodes do not agree
				else {
					list.remove(parameter1);
					//////////////////////////////////////////////
					//System.out.println("dputabort IN PROGRESS!!!!!");
					
						/////////////////////////
					//String capitalizedSentence = parameter1 + '\n';
						
					//outToClient.write(capitalizedSentence.getBytes());
					///////////////////////////////////////
					
					for (String num : ar) { 		
						
						part_one = num.split(":")[0];
						part_two = num.split(":")[1];
						
						//System.out.println(part_one);
						//System.out.println(part_two);
						
						// get all other IP addresses and ports except own, and send "dputabort" command
						if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
						
						
							clientSocket = new Socket(part_one, Integer.parseInt(part_two));
							DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
							
							sentence = "dputabort".trim() + " " + parameter1.trim();
							outToMemberServer.writeBytes(sentence + '\n');
							
							//System.out.println("dputabort Done!!!!!!");
						}
				    }
					//////////////////////////////////////////////////////////////////
				}
		
				
			}
			
			// If receive "dput1" from leader server node, then search own arraylist for "locked" keys
			else if(parameter.equals("dput1")) {

				String reply = "COMMIT!";
				
				String parameter1 = clientSentence.split(" ")[1].trim();
				
				String parameter2 = clientSentence.split(" ")[2].trim();
				
				
				for (String num : list) { 		      
			           System.out.println(num); 
			           
			           // if key is already "locked", reply "ABORT" to leader server node
			           if(parameter1.equals(num.trim())) {
			        	   reply = "ABORT!";
			        	   break;		        	   
			           }
			    }
	
				// else reply "COMMIT" to leader server node
				
				String capitalizedSentence = reply + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());
				
			}
			
			// if receive "dput2" command from leader server node
			else if (parameter.equals("dput2")) {
				
				String parameter1 = clientSentence.split(" ")[1].trim();
				String parameter2 = clientSentence.split(" ")[2].trim();
				
				// remove "locked" key from "ArrayList" and then put key and value in "HashMap"
				list.remove(parameter1);
				
				hmap.put(parameter1, parameter2);
				
				//System.out.println("Done at remote server!!!!!!!");
			}
			
			// if receive "dputabort" command from leader server node
			else if (parameter.equals("dputabort")) {
				
				String parameter1 = clientSentence.split(" ")[1].trim();

				// remove "locked" key from "ArrayList"
				list.remove(parameter1);
				
				//System.out.println("dputabort done at remote server!!!!!!!");
			}
			
			// Start of "del" command!
			else if(parameter.equals("del"))
			{
				String parameter1;

				parameter1 = clientSentence.split(" ")[1];
							
				////////////////////////////////////////////////////////////////
				// Have to a list to keep track of "locked" keys
				
				// Add "key" to "ArrayList"
				list.add(parameter1);
				////////////////////////////////////////////////////////////////
				
				////////////////////////////////////////////////////////////////
				// read in existing IP addresses and ports of servers
				//File file = new File("C:/Users/Kenneth/Desktop/Second_Assignment_2/SecondNode/nodes.cfg"); 
				
				File file = new File("/tmp/nodes.cfg"); 
				
				/////////////////////////////////////////
				//String absolute = file.getAbsolutePath(); 
				///////////////////////////////////////
				
				BufferedReader br = new BufferedReader(new FileReader(file)); 
				
				String st; 
				
				ArrayList<String> ar = new ArrayList<String>();
				
				// For debugging purposes!
				//System.out.println(ar.size());
				
				
				// Iterate through the static to load in IP addresses and ports into own "ArrayList", ar
				while ((st = br.readLine()) != null) {
				
					if(!st.equals("")) {
						ar.add(String.valueOf(st).trim());
					}
				
				} 
				br.close();
				
				/***
				for (String num : ar) { 		      
					System.out.println(num); 		
				}***/
				
				//////////////////////////////////////////////////////////////////////////////////////
				String part_one;
				String part_two;
				
				////////////////////////////////
				// Commit or don't commit!

				String reply_zero= "COMMIT!";
				////////////////////////////////
				
				String sentence="";
				
				/////////////////////////////////
				
				// Iterate through the "ArrayList", ar and send "ddel1" to all other server nodes and await reply from them.
				for (String num : ar) { 	
					part_one = num.split(":")[0];
					part_two = num.split(":")[1];
					
					//System.out.println(part_one);
					//System.out.println(part_two);
					
					// get all other IP addresses and ports except own, and send "ddel1" command
					if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
					
					
						clientSocket = new Socket(part_one, Integer.parseInt(part_two));
						DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
						
						sentence = "ddel1".trim() + " " + parameter1.trim();
						outToMemberServer.writeBytes(sentence + '\n');
						

						BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						String modifiedSentence = inFromServer.readLine();
						
						reply_zero = modifiedSentence.trim();
						
						//System.out.println(reply_zero);
					}	
				}
				
				//System.out.println("Result: " + reply_zero);
				///////////////////////////////////////////////////////////////////////////////////////
				
				// if all other server nodes agree to "COMMIT"
				if(reply_zero.equals("COMMIT!")){
					
					//////////////////////////////////////////
					
					// remove "locked" key from "ArrayList"
					list.remove(parameter1);
					
					//System.out.println("COMMIT IN PROGRESS!!!!!");
					
					// remove "key" from "HashMap"
					hmap.remove(parameter1);
						
					String capitalizedSentence = parameter1 + '\n';
						
					outToClient.write(capitalizedSentence.getBytes());
						//////////////////////							
					//sentence = "ddput2" + " " + parameter1 + " " + parameter2;
					//outToMemberServer.writeBytes(sentence + '\n');
					
					
					for (String num : ar) { 		
						
						part_one = num.split(":")[0];
						part_two = num.split(":")[1];
							
						// get all other IP addresses and ports except own, and send "ddel2" command
						if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
						
						
							clientSocket = new Socket(part_one, Integer.parseInt(part_two));
							DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
							
							sentence = "ddel2".trim() + " " + parameter1.trim();
							outToMemberServer.writeBytes(sentence + '\n');
	
							//System.out.println("Done!!!!!!");
						}
				    }
				}
				
				// else if at least one server nodes do not agree
				else {
					
					// remove "locked" value from 
					list.remove(parameter1);
					
					//////////////////////////////////////////////
					//System.out.println("ddelabort IN PROGRESS!!!!!");
					
						/////////////////////////
					//String capitalizedSentence = parameter1 + '\n';
						
					//outToClient.write(capitalizedSentence.getBytes());
					///////////////////////////////////////
					
					for (String num : ar) { 		
						
						part_one = num.split(":")[0];
						part_two = num.split(":")[1];
						
						//System.out.println(part_one);
						//System.out.println(part_two);
						
						// get all other IP addresses and ports except own, and send "ddelabort" command
						if(socket.getLocalPort() != Integer.parseInt(part_two) && !socket.getLocalAddress().equals(part_one)) {
						
						
							clientSocket = new Socket(part_one, Integer.parseInt(part_two));
							DataOutputStream outToMemberServer = new DataOutputStream(clientSocket.getOutputStream());
							
							sentence = "ddelabort".trim() + " " + parameter1.trim();
							outToMemberServer.writeBytes(sentence + '\n');
							

							//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							
							//String modifiedSentence = inFromServer.readLine();
							
							//reply_zero = modifiedSentence.trim();
							
							//System.out.println("dputabort Done!!!!!!");
						}
				    }
					//////////////////////////////////////////////////////////////////
				}
			
			}
			
			// If receive "ddel1" from leader server node, then search own arraylist for "locked" keys
			else if(parameter.equals("ddel1")) {				
				
				
				String reply = "COMMIT!";
				
				String parameter1 = clientSentence.split(" ")[1].trim();
				
		           // if key is already "locked", reply "ABORT" to leader server node
				for (String num : list) { 		      
			           System.out.println(num); 
			           
			           if(parameter1.equals(num.trim())) {
			        	   reply = "ABORT!";
			        	   break;		        	   
			           }
			    }
	
				// else reply "COMMIT" to leader server node
				String capitalizedSentence = reply + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());

			}
			
			// If receive "ddel2" from leader server node, then search own arraylist for "locked" keys
			else if (parameter.equals("ddel2")) {
				
				String parameter1 = clientSentence.split(" ")[1].trim();
				
				// remove "locked" key from "ArrayList"
				list.remove(parameter1);
				
				// remove "key" from "HashMap"
				hmap.remove(parameter1);
				
				//System.out.println("Done at remote server!!!!!!!");
			}
			
			// if receive "ddelabort" command from leader server node
			else if (parameter.equals("ddelabort")) {
				
				String parameter1 = clientSentence.split(" ")[1].trim();

				// remove "key" from "ArrayList"
				list.remove(parameter1);
				
				//System.out.println("ddelabort done at remote server!!!!!!!");
			}
			
			// if server thread receives "get" command to retrieve "value" of "key"
			else if(parameter.equals("get"))
			{
				String parameter1;
			
				parameter1 = clientSentence.split(" ")[1];
				
				String capitalizedSentence = String.valueOf(hmap.get(parameter1))  + '\n';
				
				outToClient.write(capitalizedSentence.getBytes());
			
			}
		
			// if server thread receives "store" command to retrieve all "value" and "key" pair values
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
	

