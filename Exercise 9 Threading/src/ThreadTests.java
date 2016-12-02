

public class ThreadTests {

	private static volatile int x,y,z;
	
	
	public  static  void f(){
		x = x + 1;
		y = y + 1;
		z = z + x - y;
	}
	
	
	public static void printValues() {
	    System.out.println("x = " + x);
	    System.out.println("y = " + y);
	    System.out.println("z = " + z);
	}
	
	
	public static void main(String[] args){
		
		
		Runnable secondThread = new Runnable(){

			@Override
			public void run() {
				
				for(int l = 0 ; l < 100000000 ; l ++){
					f();
				}
			}
		};
		
		Runnable firstThread = new Runnable(){

			@Override
			public void run() {

				for(int i = 0 ; i < 100000000 ; i ++){
					f();
				}
			}
		};
		
		
		Thread singleThread = new Thread(firstThread);
		Thread newThread = new Thread(secondThread);
		long startTime = System.nanoTime();
		newThread.start();
		singleThread.start();

		try {
			newThread.join();
			singleThread.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime = System.nanoTime();
		printValues();
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
		

	}
	
}
