package Client;

public class RMIClient {

	public RMIClient(String ip, int port, String operation, String key, String value) {
		try {
			System.out.println("This is a rmi client");
			Operations operations = new RMIStub(ip, port);
			String result = null;
			if(operation.equals("del")) {
				result = operations.del(key);
			}
			else if(operation.equals("put")){
				result = operations.put(key, value);
			}
			else if(operation.equals("get")){
				result = operations.get(key);
			}
			else if(operation.equals("store")){
				result = operations.store();
			}
			else if(operation.equals("exit")){
				operations.exit();
			}
			System.out.println(result);
		}catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static void main(String [] args) {      
        try {
        	RMIClient rmiclient = new RMIClient("127.0.0.1", 9000, "exit", " ", " ");  
        } catch(Throwable t){
            t.printStackTrace();      
        }      
    }    
}