package Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import Client.Operations;

public class RMIServer implements Operations{
    private Hashtable<String, String> hm;
    
    public RMIServer(Hashtable<String, String> hm) {      
        this.hm = hm;
    }      
	
	public static void main(String args[]){
		Hashtable<String, String> hm = new Hashtable<String, String>();
		RMIServer rmiserver = new RMIServer(hm);
		RMISkeleton skel = new RMISkeleton(rmiserver, 9000);
		skel.start();
	}

	public String del(String key) throws Throwable {
		// TODO Auto-generated method stub
		hm.remove(key);
		return "delete key="+key;
	}

	public String put(String key, String val) throws Throwable {
		// TODO Auto-generated method stub
		hm.put(key, val);
		return "put key="+key;
	}

	public String store() throws Throwable {
		// TODO Auto-generated method stub
		String result = "";
		Iterator hmIterator = hm.entrySet().iterator();
		if(!hmIterator.hasNext()) {
			result = "null";
		}
		
		while(hmIterator.hasNext()){
            Map.Entry element = (Map.Entry)hmIterator.next();
            String key = (String)element.getKey();
            String value = (String)element.getValue();
            result = result + "key:" + key + ":value:" + value + ":";
            if(result.getBytes().length > 65000){
                byte[] trimmedMsg = Arrays.copyOfRange(result.getBytes(), 0, 64999);
                result = "TRIMMED:" + new String(trimmedMsg);
            }
        }
		
		return result;
	}

	public String get(String key) throws Throwable {
		// TODO Auto-generated method stub
		return "get key="+key+" get val="+hm.get(key);
	}

	public void exit() throws Throwable {
		// TODO Auto-generated method stub
		System.exit(0);
	}
}
