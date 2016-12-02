import java.util.Random;

public class LogThreadTest {
	static int numberOfThreads = 31;

	static int[][] matrix = new int[numberOfThreads][10000000];
	
	static Random rand = new Random();
	static volatile int sum = 0;

	
	public synchronized static void compLog(int i){
		 for (int y = 0; y < matrix[i].length; y++) {
		        sum = sum + (int)Math.log(matrix[i][y]);
		    }
	}
	
	
	public static void main (String[] args){
		
		for (int x = 0; x < matrix.length; x++) {
		    for (int y = 0; y < matrix[x].length; y++) {
		        int randomNum = rand.nextInt(200); // 0 - 199
		        matrix[x][y] = randomNum;
		    }
		}
		
		long startTime = System.nanoTime();
		for (int x = 0; x < matrix.length; x++) {
			int i = x;
			Runnable newThread = new Runnable(){

				@Override
				public void run() {
				compLog(i);
				}
				
			};
			Thread sThread = new Thread(newThread);
			sThread.start();
			if ( x == matrix.length - 1){
				try {
					sThread.join();
					long endTime = System.nanoTime();
					System.out.println(numberOfThreads + " threads sum: " + sum);
					System.out.println("Computation took " + ((endTime - startTime)/1000000) + " milliseconds" );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		}
		
		
		
		
		
		
	}
	
	
	
	
}
