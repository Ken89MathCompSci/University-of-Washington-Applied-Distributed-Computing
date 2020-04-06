package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RMIStub implements Operations{
    private Socket socket;

    public RMIStub(String ip, int port) throws Throwable {  
        // connect to skeleton
        socket = new Socket(ip, port);
    }      
    
	public String del(String key) throws Throwable {
		// TODO Auto-generated method stub
        ObjectOutputStream outStream =      
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("del"+";"+key+";"+" ");      
        outStream.flush();
        ObjectInputStream inStream =      
            new ObjectInputStream(socket.getInputStream());      
        return (String)inStream.readObject();
	}
	public String put(String key, String val) throws Throwable {
		// TODO Auto-generated method stub
        ObjectOutputStream outStream =      
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("put"+";"+key+";"+val);
        outStream.flush();
        ObjectInputStream inStream =      
            new ObjectInputStream(socket.getInputStream());
        return (String)inStream.readObject();
	}
	public String store() throws Throwable {
		// TODO Auto-generated method stub
        ObjectOutputStream outStream =      
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("store"+";"+" "+";"+" ");      
        outStream.flush();
        ObjectInputStream inStream =      
            new ObjectInputStream(socket.getInputStream());      
        return (String)inStream.readObject();
	}
	public String get(String key) throws Throwable {
		// TODO Auto-generated method stub
        ObjectOutputStream outStream =      
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("get"+";"+key+";"+" ");      
        outStream.flush();
        ObjectInputStream inStream =      
            new ObjectInputStream(socket.getInputStream());      
        return (String)inStream.readObject();
	}
	public void exit() throws Throwable {
		// TODO Auto-generated method stub
        ObjectOutputStream outStream =      
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("exit"+";"+" "+";"+" ");
        outStream.flush();
	}


}