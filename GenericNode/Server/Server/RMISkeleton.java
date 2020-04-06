package Server;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.ServerSocket;

public class RMISkeleton extends Thread {
	private RMIServer rmiserver;
	private int port;

	public RMISkeleton(RMIServer server, int port) {
		// get reference of object server
		this.rmiserver = server;
		this.port = port;
	}

	public void run() {
		try {

			// new socket at port 9000
			ServerSocket serverSocket = new ServerSocket(port);

			while (true) {
				// accept stub's request
				Socket socket = serverSocket.accept();
				
				while (socket != null) {
					// get stub's request
					ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
					String received = (String) inStream.readObject();
					String operation = received.split(";")[0];
					String result;
					if (operation.equals("get")) {
						// execute object server's business method
						String key = received.split(";")[1];
						result = rmiserver.get(key);
						ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
						// return result to stub
						outStream.writeObject(result);
						outStream.flush();
					} else if (operation.equals("put")) {
						String key = received.split(";")[1];
						String val = received.split(";")[2];
						result = rmiserver.put(key, val);
						ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
						// return result to stub
						outStream.writeObject(result);
						outStream.flush();

					} else if (operation.equals("del")) {
						String key = received.split(";")[1];
						result = rmiserver.del(key);
						ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
						// return result to stub
						outStream.writeObject(result);
						outStream.flush();
					} else if (operation.equals("store")) {
						// execute object server's business method
						result = rmiserver.store();
						ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
						// return result to stub
						outStream.writeObject(result);
						outStream.flush();
					} else if (operation.equals("exit")) {
						rmiserver.exit();
//						ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
//						// return result to stub
//						outStream.writeObject(result);
//						outStream.flush();
					}
					socket = null;
				}
				
			}

		} catch (Throwable t) {
			System.exit(0);
			t.printStackTrace();
		}
	}
}