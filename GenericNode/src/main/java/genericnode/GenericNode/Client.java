package genericnode.GenericNode;

import java.io.*;
import java.net.*;

public class Client extends Thread
{
    private Socket connectionSocket;
    private String clientSentence;
    private String capitalizedSentence;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
 
    public Client(Socket c) throws IOException
    {
        connectionSocket = c;
    }
 
    public void run() 
    {
        try
        {    
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }
        catch(IOException e)
        {
            System.out.println("Errore: " + e);
        }
    }
}

