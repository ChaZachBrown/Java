
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HelloWorld {
	public static void main(String[]args){
		// calling the method for hello world. Part A
	Hello();
	
	// Prompting user to input array of integers and then parsing them into ArrayList 
	System.out.println("Enter an array of integers using spaces between each integer. Use q and then hit enter to end input!");
	Scanner in = new Scanner(System.in);
	ArrayList<Integer> inputList = new ArrayList<Integer>();
	while(in.hasNextInt()){
		inputList.add(in.nextInt());
	};
	
	System.out.println("This is your array of integers " + inputList);
	System.out.println("This is your array of integers reversed " + reverse(inputList));//calling reverse method and printing reversed array
	
	//prompting user to input integer to be used from grid
	System.out.println("Please enter in an integer!");
	Scanner  in2 = new Scanner(System.in);
	
	//calling grid method
	grid(in2.nextInt());
	
	
	}
	// method to ask for name and say hello back
	public static void Hello(){
		System.out.print("What is your name?");
		Scanner in = new Scanner(System.in); // creating a new input stream to read from keyboard
		String name = in.nextLine();//parsing 
		System.out.print("Hello, "+name + "!" + '\n');
	}
	//method to take in array and return it reversed
	public static ArrayList<Integer> reverse(ArrayList<Integer> inputList){
		Collections.reverse(inputList);
		
		
	return inputList;
		
	}
	//method to take in int and output gri ixi
	public static void grid (int q){
		for(int r = q ; r!=0 ; r--){//outer for loop to run int number of times
			for(int y = q; y !=0; y--){//inner for loop to make the +-- layers
				System.out.print("+--");
			}
			System.out.print("+\n");//putting last + on the row and going to new line
			for(int w = q; w != -1; w--){//inner for loop to make the |
				System.out.print("|  ");
			}
			System.out.print("\n");// going to next line
		}
		for(int r = q; r!=0; r--){//loop to put the last row of +--
			System.out.print("+--");
		}
		System.out.print("+ \n");//putting the last + on and going to new line
		}
	};
	