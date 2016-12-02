import java.lang.reflect.Field;
import acme.NetworkService;


public class main {
	public static void main(String[] args) {
        NetworkService ns = new NetworkService();
        getServerName(ns);
        changeServerName(ns);
        ns.connect();
    }
	
	/**
	 * post-conditions: the class of the object and all the fields will be be printed to the console 
	 * @param n
	 */
	public static  void getServerName(NetworkService  n){
		//Iterates through all of the fields in the NetworkService object
		//and prints their name out to console
		
		for(Field f : n.getClass().getDeclaredFields()) {
			   System.out.println(f.toGenericString());
			  } 
	}
	
	/**
	 * post-condition: changes the value of the machineName method in the NetworkService to "aws.com"
	 * @param n
	 */
	public static void changeServerName(NetworkService n){
		// changes the value of the machineName method to "aws.com"
		try {
			Field fieldToChange = n.getClass().getDeclaredField("machineName");
			fieldToChange.setAccessible(true);
			fieldToChange.set(n, "aws.com");
			fieldToChange.setAccessible(false);

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}